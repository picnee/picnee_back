package com.picnee.travel.api.in;

import com.picnee.travel.domain.postComment.dto.req.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.req.UpdatePostCommentReq;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "posts/comments", description = "postComment API")
public interface PostCommentApi {

    @Operation(summary = "댓글 생성", description = "댓글을 생성한다.")
    public ResponseEntity<String> createPostComment(UUID postId, CreatePostCommentReq dto, AuthenticatedUserReq auth);

    @Operation(summary = "댓글 수정", description = "댓글을 수정한다.")
    public ResponseEntity<String> updatePostComment(UUID postId, UUID commentId,
                                                    UpdatePostCommentReq dto,
                                                    AuthenticatedUserReq auth);

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제한다.")
    public ResponseEntity<Void> deletePostComment(UUID postId, UUID commentId, AuthenticatedUserReq auth);
}
