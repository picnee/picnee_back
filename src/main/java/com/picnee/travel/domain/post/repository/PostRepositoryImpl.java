package com.picnee.travel.domain.post.repository;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.entity.QPost;
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
    public Page<Post> findByPosts(Pageable pageable) {
        QPost post = QPost.post;

        JPAQuery<Post> query = jpaQueryFactory
                .selectFrom(post)
                .where(post.isDeleted.isFalse())
                .orderBy(post.createdAt.desc());

        List<Post> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(jpaQueryFactory
                        .select(post.count())
                        .from(post)
                        .where(post.isDeleted.isFalse())
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(result, pageable, total);
    }
}
