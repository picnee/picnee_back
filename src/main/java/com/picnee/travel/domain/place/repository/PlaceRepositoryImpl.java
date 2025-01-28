package com.picnee.travel.domain.place.repository;

import com.picnee.travel.domain.place.dto.res.FilterPlaceRes;
import com.picnee.travel.domain.place.entity.PlaceType;
import com.picnee.travel.domain.place.entity.QOpeningHours;
import com.picnee.travel.domain.place.entity.QPlace;
import com.picnee.travel.domain.place.entity.Region;
import com.picnee.travel.domain.review.entity.QReview;
import com.picnee.travel.domain.review.entity.QReviewVoteAccommodation;
import com.picnee.travel.domain.review.entity.QReviewVoteRestaurant;
import com.picnee.travel.domain.review.entity.QReviewVoteTouristspot;
import com.picnee.travel.domain.usersReview.entity.QUsersReview;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

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
        QReviewVoteRestaurant reviewVoteRestaurant = QReviewVoteRestaurant.reviewVoteRestaurant;
        QReviewVoteAccommodation reviewVoteAccommodation = QReviewVoteAccommodation.reviewVoteAccommodation;
        QReviewVoteTouristspot reviewVoteTouristspot = QReviewVoteTouristspot.reviewVoteTouristspot;

        List<String> filterList = new ArrayList<>();

        // vote 필터링
        if ( !filters.isEmpty() ) {
            JPAQuery<Tuple> filterQuery = new JPAQuery<>(entityManager);
            filterQuery.from(place)
                    .innerJoin(review).on(place.id.eq(review.place.id))
                    .where(place.region.eq(Region.valueOf(region.toUpperCase()))
                            .and(place.types.eq(PlaceType.valueOf(type.toUpperCase()))));

            List<Expression<?>> filterExpression = new ArrayList<>();
            filterExpression.add(place.id);

            switch(type.toUpperCase()) {
                case "RESTAURANT" :
                    filterQuery.innerJoin(reviewVoteRestaurant).on(review.id.eq(reviewVoteRestaurant.id));
                    addFilterExperssion(reviewVoteRestaurant, filters, filterExpression);
                    break;
                case "ACCOMODATION" :
                    filterQuery.innerJoin(reviewVoteAccommodation).on(review.id.eq(reviewVoteAccommodation.id));
                    addFilterExperssion(reviewVoteAccommodation, filters, filterExpression);
                    break;
                case "TOURISTSPOT" :
                    filterQuery.innerJoin(reviewVoteTouristspot).on(review.id.eq(reviewVoteTouristspot.id));
                    addFilterExperssion(reviewVoteTouristspot, filters, filterExpression);
                    break;
            }

            filterQuery.select(filterExpression.toArray(new Expression[0]))
                      .groupBy(place.id);

            List<Tuple> filterQueryResult = filterQuery.fetch();

            // Tuple을 필터링 점수에 따라 true/false 판별해서 대상 place_id만 List로 만들기
            // 0점인 경우는 제외하기
            // key를 전부 순회하면서 하나라도 안 맞으면 List에 넣지 않기 => Tuple의 index는 1부터 시작해서 키의 개수만큼 외부 for문에 선언

            for (Tuple tuple : filterQueryResult) {
                int index = 1;
                boolean target = false;

                for (Map.Entry<String, Boolean> entry : filters.entrySet()) {
                    String key = entry.getKey();
                    Boolean value = entry.getValue();
                    Integer sumValue = tuple.get(index, Integer.class);

                    if ( value ) {
                        target = sumValue != null && sumValue > 0;
                    } else {
                        target = sumValue != null && sumValue < 0;
                    }

                    if ( !target ) break;

                    index++;
                }

                if ( target ) {
                    filterList.add(tuple.get(0, String.class));
                }
            }

            log.info("@@@@@filterList = {}", filterList);
        }

        BooleanExpression filterCondition = filters.isEmpty() ? null : review.place.id.in(filterList);

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
                .where(filterCondition)
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
            .where(filterCondition)
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

    private <T> void addFilterExperssion(T voteEntity, Map<String, Boolean> filters, List<Expression<?>> filterExpression) {
        for( Map.Entry<String, Boolean> entry : filters.entrySet() ) {
            String key = entry.getKey();

            Boolean value = entry.getValue();

            BooleanPath field = (BooleanPath) getField(voteEntity, key);

            NumberExpression<Integer> caseExpression = new CaseBuilder()
                    .when(field.eq(true)).then(1)
                    .otherwise(-1)
                    .sum()
                    .as(key);

            filterExpression.add(caseExpression);
        }
    }

    private Object getField(Object entity, String fieldName) {
        try {
            return entity.getClass().getDeclaredField(fieldName).get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Field not found: " + fieldName, e);
        }
    }
}
