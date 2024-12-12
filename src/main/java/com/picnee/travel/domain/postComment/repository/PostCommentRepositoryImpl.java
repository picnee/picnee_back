package com.picnee.travel.domain.postComment.repository;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.entity.QPostComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
}
