package com.picnee.travel.domain.postComment.service;

import com.picnee.travel.domain.board.entity.BoardCategory;
import com.picnee.travel.domain.board.entity.Region;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.entity.CreateTestPost;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.postComment.dto.req.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.CreateTestUser;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PostCommentServiceTest {

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    CreateTestUser createTestUser;

    @Autowired
    CreateTestPost createTestPost;

    private AuthenticatedUserReq user;
    private AuthenticatedUserReq anotherUser;
    private Post post;
    private PostComment postComment;

    @BeforeEach
    void setUp() throws Exception {
        user = createTestUser.createUser();
        anotherUser = createTestUser.createAnotherUser();
        post = createTestPost.createPost(user);

        CreatePostCommentReq req = CreatePostCommentReq.builder()
                .content("댓글입니다.")
                .build();

        postComment = postCommentService.create(post.getId(), req, user);
    }

    @Test
    @DisplayName("댓글을 생성한다.")
    void test1() {
        assertThat(postComment).isNotNull();
        assertThat(postComment.getContent()).isEqualTo("댓글입니다.");
        assertThat(postComment.getUser().getId()).isEqualTo(user.getId());
    }

}