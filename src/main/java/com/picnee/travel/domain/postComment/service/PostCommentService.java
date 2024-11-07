package com.picnee.travel.domain.postComment.service;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.postComment.dto.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.repository.PostCommentRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommentService {

    private final UserService userService;
    private final PostCommentRepository postCommentRepository;
    private final PostService postService;

    @Transactional
    public PostComment create(UUID postId, CreatePostCommentReq dto, AuthenticatedUserReq auth) {

        System.out.println("auth :" + auth.toString());

        User user = userService.findByEmail(auth.getEmail());
        Post post = postService.findById(postId);

        return postCommentRepository.save(CreatePostCommentReq.toEntity(dto, user, post));
    }
}
