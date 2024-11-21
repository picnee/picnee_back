package com.picnee.travel.domain.usersPost.service;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.usersPost.entity.UsersPost;
import com.picnee.travel.domain.usersPost.repository.UsersPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersPostService {

    private final UsersPostRepository usersPostRepository;

    @Transactional(propagation = REQUIRES_NEW)
    public boolean isFirstView(Post post, User user) {
        Optional<UsersPost> existingUsersPost = usersPostRepository.findByPostAndUser(post, user);

        if (existingUsersPost.isPresent()) {
            return false;
        }

        UsersPost usersPost = UsersPost.builder()
                .post(post)
                .user(user)
                .isViewed(true)
                .build();

        usersPostRepository.save(usersPost);

        return true;
    }
}
