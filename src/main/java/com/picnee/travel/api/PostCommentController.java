package com.picnee.travel.api;

import com.picnee.travel.api.in.PostCommentApi;
import com.picnee.travel.domain.postComment.dto.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.service.PostCommentService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostCommentController implements PostCommentApi {

    private final PostCommentService postCommentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createPostComment(@PathVariable("postId") UUID postId, @Valid @RequestBody CreatePostCommentReq dto, @AuthenticatedUser AuthenticatedUserReq auth) {

        if (dto.existCommentParent()) {
            //TODO : 대댓글 로직
        }

        PostComment postComment = postCommentService.create(postId, dto, auth);
        return ResponseEntity.status(CREATED).body(postComment.getId().toString());
    }



}

