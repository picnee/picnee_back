package com.picnee.travel.domain.review.repository;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.review.entity.QReview;
import com.picnee.travel.domain.review.entity.Review;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Review> findByReviewOfPlace(Place place, String sort, Pageable pageable) {
        QReview review = QReview.review;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(review.isDeleted.isFalse());

        JPAQuery<Review> query = jpaQueryFactory
                .selectFrom(review)
                .where(builder);

        switch (sort) {
            case "new" -> query.orderBy(review.createdAt.desc());
            case "old" -> query.orderBy(review.createdAt.asc());
            case "high" -> query.orderBy(review.rating.desc(), review.createdAt.desc());
            case "low" -> query.orderBy(review.rating.asc(), review.createdAt.desc());
            default -> query.orderBy(review.createdAt.desc());
        }

        List<Review> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(jpaQueryFactory
                        .select(review.count())
                        .from(review)
                        .where(review.place.id.eq(place.getId()))
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(result, pageable, total);

    }
}
