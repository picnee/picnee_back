package com.picnee.travel.domain.post.repository;

import com.picnee.travel.domain.board.entity.BoardCategory;
import com.picnee.travel.domain.board.entity.Region;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.entity.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> findByPosts(String boardCategory, String region, Pageable pageable) {
        QPost post = QPost.post;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(post.isDeleted.isFalse());

        if (boardCategory != null && !boardCategory.isEmpty()) {
            builder.and(post.board.boardCategory.eq(BoardCategory.fromString(boardCategory)));
        }

        if (region != null && !region.isEmpty()) {
            builder.and(post.board.region.eq(Region.fromString(region)));
        }

        JPAQuery<Post> query = jpaQueryFactory
                .selectFrom(post)
                .where(builder)
                .orderBy(post.createdAt.desc());

        List<Post> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(jpaQueryFactory
                        .select(post.count())
                        .from(post)
                        .where(builder) // 동적 조건 동일하게 사용
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(result, pageable, total);
    }
}
