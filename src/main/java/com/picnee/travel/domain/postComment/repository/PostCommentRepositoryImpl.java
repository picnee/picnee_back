package com.picnee.travel.domain.postComment.repository;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.entity.QPost;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.entity.QPostComment;
import com.picnee.travel.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostComment> findByCommentsOfPost(Post post) {
        QPostComment postComment = QPostComment.postComment;
        QPostComment childComment = new QPostComment("childComment");

        return jpaQueryFactory.selectFrom(postComment)
                .leftJoin(postComment.children, childComment).fetchJoin()
                .where(postComment.post.eq(post)
                        .and(postComment.isDeleted.isFalse())
                        .and(postComment.commentParent.isNull()))
                .orderBy(postComment.createdAt.asc())
                .fetch();
    }

    @Override
    public List<PostComment> findChildrenByParentId(UUID commentId) {
        QPostComment postComment = QPostComment.postComment;

        return jpaQueryFactory
                .selectFrom(postComment)
                .where(postComment.commentParent.id.eq(commentId)
                        .and(postComment.isDeleted.isFalse()))
                .fetch();
    }

    @Override
    public Page<PostComment> getMyComments(Pageable pageable, UUID userId) {
        QPostComment postComment = QPostComment.postComment;

        JPAQuery<PostComment> query = jpaQueryFactory
                .selectFrom(postComment)
                .where(postComment.isDeleted.isFalse(),
                        postComment.user.id.eq(userId))
                .orderBy(postComment.createdAt.desc());

        List<PostComment> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(jpaQueryFactory
                        .select(postComment.count())
                        .from(postComment)
                        .where(postComment.isDeleted.isFalse(),
                                postComment.user.id.eq(userId)) // 동적 조건 동일하게 사용
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(result, pageable, total);
    }
}
