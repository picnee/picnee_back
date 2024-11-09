package com.picnee.travel.domain.postComment.repository;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.entity.QPostComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostComment> findByCommentsOfPost(Post post) {
        QPostComment postComment = QPostComment.postComment;
        QPostComment childComment = new QPostComment("childComment");

        return jpaQueryFactory.selectFrom(postComment)
                .leftJoin(postComment.children, childComment).fetchJoin()  // Fetch replies (nested comments)
                .where(postComment.post.eq(post)
                        .and(postComment.isDeleted.isFalse())
                        .and(postComment.commentParent.isNull()))  // Fetch only parent comments
                .orderBy(postComment.createdAt.desc())  // Optional: order by creation time
                .fetch();
    }
}
