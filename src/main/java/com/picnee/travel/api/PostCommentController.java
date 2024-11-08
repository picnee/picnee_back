package com.picnee.travel.api;

import com.picnee.travel.api.in.PostCommentApi;
import com.picnee.travel.domain.postComment.dto.req.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.req.UpdatePostCommentReq;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.service.PostCommentService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostCommentController implements PostCommentApi {

    private final PostCommentService postCommentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createPostComment(@PathVariable("postId") UUID postId,
                                                    @Valid @RequestBody CreatePostCommentReq dto,
                                                    @AuthenticatedUser AuthenticatedUserReq auth) {
        PostComment postComment = postCommentService.create(postId, dto, auth);
        return ResponseEntity.status(CREATED).body(postComment.getId().toString());
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> updatePostComment(@PathVariable("postId") UUID postId,
                                                    @PathVariable("commentId") UUID commentId,
                                                    @Valid @RequestBody UpdatePostCommentReq dto,
                                                    @AuthenticatedUser AuthenticatedUserReq auth) {
        postCommentService.update(postId, commentId, dto, auth);
        return ResponseEntity.status(NO_CONTENT).build();
    }


}

