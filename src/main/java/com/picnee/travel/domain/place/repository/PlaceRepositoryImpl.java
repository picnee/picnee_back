package com.picnee.travel.domain.place.repository;

import com.picnee.travel.domain.place.dto.res.FilterPlaceRes;
import com.picnee.travel.domain.place.entity.PlaceType;
import com.picnee.travel.domain.place.entity.QOpeningHours;
import com.picnee.travel.domain.place.entity.QPlace;
import com.picnee.travel.domain.place.entity.Region;
import com.picnee.travel.domain.review.entity.QReview;
import com.picnee.travel.domain.usersReview.entity.QUsersReview;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public List<FilterPlaceRes> filterPlaces(String region, String type, String sort, Map<String, Boolean> filters) {
        QPlace place = QPlace.place;
        QOpeningHours openingHours = QOpeningHours.openingHours;
        QReview review = QReview.review;
        QUsersReview usersReview = QUsersReview.usersReview;
        QReview bestReview = new QReview("bestReview");

        List<Tuple> subQuery = jpaQueryFactory
                .select(
                        review.id,
                        review.place.id,
                        new CaseBuilder()
                                .when(usersReview.goodAndBad.eq(true)).then(2)
                                .otherwise(0)
                                .add(
                                        new CaseBuilder()
                                                .when(usersReview.goodAndBad.eq(false)).then(-1)
                                                .otherwise(0)
                                )
                                .sum().as("score")
                )
                .from(review)
                .leftJoin(usersReview).on(usersReview.review.id.eq(review.id))
                .groupBy(review.id, review.place.id)
                .fetch();

        List<UUID> scorestest = subQuery.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(review.place.id),
                        Collectors.maxBy(Comparator.comparing(tuple ->
                                tuple.get(Expressions.numberPath(Integer.class, "score"))
                        ))
                ))
                .values()
                .stream()
                .filter(Optional::isPresent)
                .map(optional -> optional.get().get(review.id))
                .collect(Collectors.toList());

        JPQLQuery<FilterPlaceRes> query = new JPAQuery<>(entityManager)
            .select(
                Projections.constructor(
                    FilterPlaceRes.class,
                    place.id.as("placeId"),
                    bestReview.id.as("reviewId"),
                    place.placeName,
                    place.types.as("placeType"),
                    review.rating.avg().as("reviewRating"),
                    review.id.countDistinct().as("reviewCount"),
                    bestReview.goodPoints.as("mostPopularGoodPoints")
                )
            )
            .from(place)
            .leftJoin(openingHours).on(openingHours.place.id.eq(place.id))
            .leftJoin(review).on(review.place.id.eq(place.id))
            .leftJoin(bestReview).on(bestReview.place.id.eq(place.id).and(bestReview.id.in(scorestest)))
            .groupBy(place.id, bestReview.id)
            .orderBy(review.id.countDistinct().desc());

        // 인기순과 평점순으로 정렬
        if ("review".equals(sort)) {
            query.orderBy(review.id.count().desc());
        } else if ("rating".equals(sort)) {
            query.orderBy(review.rating.avg().desc());
        }

        // 지역 필터링
        Optional.ofNullable(region)
                .ifPresent(r -> query.where(review.place.region.eq(Region.valueOf(r.toUpperCase()))));

        // 타입 필터링
        Optional.ofNullable(type)
                .ifPresent(r -> query.where(review.place.types.eq(PlaceType.valueOf(r.toUpperCase()))));

        return query.fetch();
    }

//    private BooleanExpression getFilterByType(String type, String key, Boolean expectedValue, QPlace place, QReview review) {
//        switch (type) {
//            case "restaurants":
//                return getRestaurantFilter(key, expectedValue, review);
//            case "tourist-spots":
//                return getTouristSpotFilter(key, expectedValue, review);
//            case "accommodations":
//                return getAccommodationFilter(key, expectedValue, review);
//            default:
//                throw new IllegalArgumentException("Unsupported type: " + type);
//        }
//    }
//
//    private BooleanExpression getRestaurantFilter(String key, Boolean expectedValue, QReview review) {
//        switch (key) {
//            case "has_kiosk":
//                return createAggregateCondition(review.kiosk, expectedValue);
//            case "has_card_payment":
//                return createAggregateCondition(review.cardPayment, expectedValue);
//            case "has_smoking_area":
//                return createAggregateCondition(review.smokingArea, expectedValue);
//            default:
//                return null;
//        }
//    }
//
//    private BooleanExpression getTouristSpotFilter(String key, Boolean expectedValue, QReview review) {
//        switch (key) {
//            case "has_parking":
//                return createAggregateCondition(review.parking, expectedValue);
//            case "has_reservations":
//                return createAggregateCondition(review.reservations, expectedValue);
//            default:
//                return null;
//        }
//    }
//
//    private BooleanExpression getAccommodationFilter(String key, Boolean expectedValue, QReview review) {
//        switch (key) {
//            case "has_self_check_in_out":
//                return createAggregateCondition(review.selfCheckInOut, expectedValue);
//            case "has_24hr_service":
//                return createAggregateCondition(review.service24Hr, expectedValue);
//            default:
//                return null;
//        }
//    }
//
//    private BooleanExpression createAggregateCondition(BooleanPath reviewColumn, Boolean expectedValue) {
//        return JPAExpressions.select(reviewColumn.count())
//                .from(QReview.review)
//                .where(reviewColumn.eq(expectedValue))
//                .groupBy(QReview.review.place.id)
//                .having(reviewColumn.count().gt(0)); // 기본 조건: true가 많은 경우 반환
//    }
}
