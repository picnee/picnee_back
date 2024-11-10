package com.picnee.travel.domain.postComment.repository;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostCommentRepositoryCustom {
    List<PostComment> findByCommentsOfPost(Post post);
}
