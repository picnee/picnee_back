package com.picnee.travel.api.in;

import com.picnee.travel.domain.postComment.dto.req.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.req.UpdatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.res.GetPostCommentRes;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;
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

    @Operation(summary = "댓글 조회", description = "댓글을 조회한다.")
    public ResponseEntity<List<GetPostCommentRes>> getPostComment(UUID postId, AuthenticatedUserReq auth);

    @Operation(summary = "대댓글 생성", description = "대댓글을 생성한다.")
    public ResponseEntity<String> createChildrenComment(UUID postId, UUID commentId, CreatePostCommentReq dto, AuthenticatedUserReq auth);
}
