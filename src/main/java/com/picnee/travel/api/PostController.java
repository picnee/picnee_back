package com.picnee.travel.api;

import com.picnee.travel.api.in.PostApi;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody CreatePostReq dto,
                                           @AuthenticatedUser AuthenticatedUserReq auth) {
        postService.create(dto, auth);
        return ResponseEntity.status(CREATED).build();
    }

}
