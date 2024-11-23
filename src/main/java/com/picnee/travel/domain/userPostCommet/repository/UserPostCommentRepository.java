package com.picnee.travel.domain.userPostCommet.repository;

import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.userPostCommet.entity.UserPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPostCommentRepository extends JpaRepository<UserPostComment, Long> {
    Optional<UserPostComment> findByUserAndPostComment(User user, PostComment postComment);
}
