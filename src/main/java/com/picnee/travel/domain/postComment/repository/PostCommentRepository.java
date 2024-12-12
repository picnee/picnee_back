package com.picnee.travel.domain.postComment.repository;

import com.picnee.travel.domain.postComment.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, UUID>, PostCommentRepositoryCustom{

}
