package com.picnee.travel.domain.usersPost.repository;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.usersPost.entity.UsersPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersPostRepository extends JpaRepository<UsersPost, UUID> {
    Optional<UsersPost> findByPostAndUser(Post post, User user);
}
