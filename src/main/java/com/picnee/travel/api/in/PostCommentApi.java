package com.picnee.travel.api.in;

import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.dto.req.ModifyPostReq;
import com.picnee.travel.domain.postComment.dto.CreatePostCommentReq;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "posts/comments", description = "postComment API")
public interface PostCommentApi {

    @Operation(summary = "댓글 생성", description = "댓글을 생성한다.")
    public ResponseEntity<String> createPostComment(UUID postId, CreatePostCommentReq dto, AuthenticatedUserReq auth);

}
