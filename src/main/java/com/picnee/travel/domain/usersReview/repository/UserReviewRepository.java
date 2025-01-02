package com.picnee.travel.domain.usersReview.repository;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.userPostCommet.entity.UserPostComment;
import com.picnee.travel.domain.usersReview.entity.UsersReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReviewRepository extends JpaRepository<UsersReview, Long> {
    Optional<UsersReview> findByUserAndReview(User user, Review review);
}
