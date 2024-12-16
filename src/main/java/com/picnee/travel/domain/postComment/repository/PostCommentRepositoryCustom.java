package com.picnee.travel.domain.postComment.repository;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostCommentRepositoryCustom {
    List<PostComment> findByCommentsOfPost(Post post);

    void findChildrenByParentId(UUID commentId);

    Page<PostComment> getMyComments(Pageable pageable, UUID userId);
}
