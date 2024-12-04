package com.picnee.travel.domain.post.entity;

import com.picnee.travel.domain.board.entity.BoardCategory;
import com.picnee.travel.domain.board.entity.Region;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Component
public class CreateTestPost {

    @Autowired
    private PostService postService;

    private CreatePostReq createPostReq;

    /**
     * 테스트 게시글 생성
     * */
    public Post createPost(AuthenticatedUserReq auth) {
        CreatePostReq postReq = CreatePostReq.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .region(Region.KANSAI)
                .boardCategory(BoardCategory.RESTAURANT)
                .build();

        return postService.create(postReq, auth);
    }
}
