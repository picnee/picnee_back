package com.picnee.travel.domain.postComment.service;

import com.picnee.travel.domain.post.entity.CreateTestPost;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.dto.req.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.req.UpdatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.res.GetPostCommentRes;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.exception.NotProvideCommentLikeException;
import com.picnee.travel.domain.postComment.exception.NotValidOwnerException;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.CreateTestUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.picnee.travel.global.exception.ErrorCode.NOT_VALID_OWNER_EXCEPTION;
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

    @Test
    @DisplayName("댓글을 수정한다.")
    void test2() {
        UpdatePostCommentReq req = UpdatePostCommentReq.builder()
                .content("수정된 댓글입니다.")
                .build();

        postCommentService.update(post.getId(), postComment.getId(), req, user);

        assertThat(postComment).isNotNull();
        assertThat(postComment.getContent()).isEqualTo("수정된 댓글입니다.");
        assertThat(postComment.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("댓글 수정 테스트 실패 : 다른 사람의 댓글은 수정하지 못한다.")
    void test3() {
        UpdatePostCommentReq req = UpdatePostCommentReq.builder()
                .content("수정된 댓글입니다.")
                .build();

        assertThatThrownBy(() -> postCommentService.update(post.getId(), postComment.getId(), req, anotherUser))
                .isInstanceOf(NotValidOwnerException.class);
    }

    @Test
    @DisplayName("댓글 삭제한다.")
    void test4() {
        postCommentService.delete(post.getId(), postComment.getId(), user);
        List<GetPostCommentRes> comments = postCommentService.getComments(post.getId(), user);

        assertThat(comments).isEmpty();
    }

    @Test
    @DisplayName("댓글 삭제 실패 : 다른 사람의 댓글은 삭제하지 못한다.")
    void test5() {
        assertThatThrownBy(() -> postCommentService.delete(post.getId(), postComment.getId(), anotherUser))
                .isInstanceOf(NotValidOwnerException.class);
    }

    @Test
    @DisplayName("댓글 조회 한다.")
    void test6() {
        for (int i = 0; i < 10; i++){
            CreatePostCommentReq req = CreatePostCommentReq.builder()
                    .content("댓글입니다.")
                    .build();

            postComment = postCommentService.create(post.getId(), req, user);
        }

        List<GetPostCommentRes> comments = postCommentService.getComments(post.getId(), user);
        assertThat(comments.size()).isEqualTo(11L);
    }

    @Test
    @DisplayName("좋아요 성공 테스트")
    void test7() {
        postCommentService.toggleLike(post.getId(), postComment.getId(), user);
        PostComment likeComment = postCommentService.findById(postComment.getId());

        assertThat(likeComment.getLikes()).isEqualTo(1L);
    }

    @Test
    @DisplayName("좋아요는 두번 누르면 좋아요 취소 테스트")
    void test8() {
        postCommentService.toggleLike(post.getId(), postComment.getId(), user);
        PostComment likeComment = postCommentService.findById(postComment.getId());
        // user는 좋아요를 취소했다.
        postCommentService.toggleLike(post.getId(), postComment.getId(), user);
        // anotherUser는 좋아요를 했다.
        postCommentService.toggleLike(post.getId(), postComment.getId(), anotherUser);

        assertThat(likeComment.getLikes()).isEqualTo(1L);
    }

    @Test
    @DisplayName("좋아요 2개 테스트")
    void test9() {
        // user는 좋아요를 눌렀다.
        postCommentService.toggleLike(post.getId(), postComment.getId(), user);
        // anotherUser는 좋아요를 했다.
        postCommentService.toggleLike(post.getId(), postComment.getId(), anotherUser);
        PostComment likeComment = postCommentService.findById(postComment.getId());

        assertThat(likeComment.getLikes()).isEqualTo(2L);
    }

    @Test
    @DisplayName("좋아요 실패 : 로그인한 사용자만 댓글 좋아요가 가능하다.")
    void test10() {
        assertThatThrownBy(() -> postCommentService.toggleLike(post.getId(), postComment.getId(), null))
                .isInstanceOf(NotProvideCommentLikeException.class);
    }
}