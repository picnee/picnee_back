package com.picnee.travel.domain.post.service;

import com.picnee.travel.domain.board.entity.BoardCategory;
import com.picnee.travel.domain.board.entity.Region;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.dto.req.ModifyPostReq;
import com.picnee.travel.domain.post.dto.res.FindPostRes;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.exception.NotPostAuthorException;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.CreateTestUser;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    CreateTestUser createTestUser;

    private AuthenticatedUserReq user;
    private AuthenticatedUserReq anotherUser;
    private JwtTokenRes jwtTokenRes;
    private Post post;

    @BeforeEach
    void setUp() throws Exception {
        user = createTestUser.createUser();
        anotherUser = createTestUser.createAnotherUser();
        jwtTokenRes = createTestUser.createJwtToken();

        CreatePostReq postReq = CreatePostReq.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .region(Region.KANSAI)
                .boardCategory(BoardCategory.RESTAURANT)
                .build();

        post = postService.create(postReq, user);
    }

    @Test
    @DisplayName("자유 토크 게시글 생성 성공")
    void test1() {
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("테스트 제목");
        assertThat(post.getContent()).isEqualTo("테스트 내용");
    }

    @Test
    @DisplayName("자유 토크 게시글 수정 성공")
    void test2() {
        ModifyPostReq modifyPostReq = ModifyPostReq.builder()
                .title("수정된 테스트 제목")
                .content("수정된 테스트 내용")
                .region(Region.KANSAI)
                .boardCategory(BoardCategory.RESTAURANT)
                .build();

        postService.update(post.getId(), modifyPostReq, user);

        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("수정된 테스트 제목");
        assertThat(post.getContent()).isEqualTo("수정된 테스트 내용");
    }

    @Test
    @DisplayName("자유 토크 게시글 수정 실패 : 게시글 작성자가 아닌 다른 유저가 수정할려고 할 경우")
    void test3() {
        ModifyPostReq modifyPostReq = ModifyPostReq.builder()
                .title("수정된 테스트 제목")
                .content("수정된 테스트 내용")
                .region(Region.KANSAI)
                .boardCategory(BoardCategory.RESTAURANT)
                .build();

        assertThatThrownBy(() -> postService.update(post.getId(), modifyPostReq, anotherUser))
                .isInstanceOf(NotPostAuthorException.class);
    }

    @Test
    @DisplayName("자유 토크 게시글 삭제 성공")
    void test4() {
        postService.delete(post.getId(), user);
        Page<FindPostRes> posts = postService.findPosts(null, null, 0);
        assertThat(posts.getTotalElements()).isEqualTo(0L);
    }

    @Test
    @DisplayName("자유 토크 게시글 삭제 실패 : 권한 없는 유저가 삭제할 경우")
    void test5() {
        assertThatThrownBy(() -> postService.delete(post.getId(), anotherUser))
                .isInstanceOf(NotPostAuthorException.class);

        Page<FindPostRes> posts = postService.findPosts(null, null, 0);
        assertThat(posts.getTotalElements()).isEqualTo(1L);
    }

    @Test
    @DisplayName("로그인을 하지 않더라도 게시글 조회는 성공")
    void test6() {
        FindPostRes findPostRes = postService.find(post.getId(), null);

        assertThat(findPostRes).isNotNull();
        assertThat(findPostRes.getTitle()).isEqualTo("테스트 제목");
        assertThat(findPostRes.getContent()).isEqualTo("테스트 내용");
    }

}