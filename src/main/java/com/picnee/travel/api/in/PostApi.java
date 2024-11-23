package com.picnee.travel.api.in;

import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.dto.req.ModifyPostReq;
import com.picnee.travel.domain.post.dto.res.FindPostRes;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "posts", description = "post API")
public interface PostApi {

    @Operation(summary = "게시글 생성", description = "게시글을 생성한다.")
    public ResponseEntity<String> createPost(CreatePostReq dto, AuthenticatedUserReq auth);

    @Operation(summary = "게시글 수정", description = "게시글을 수정한다.")
    public ResponseEntity<String> updatePost(UUID postId, ModifyPostReq dto, AuthenticatedUserReq auth);

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제한다.")
    public ResponseEntity<Void> deletePost(UUID postId, AuthenticatedUserReq auth);

    @Operation(summary = "게시글 조회", description = "게시글을 조회한다.")
    public ResponseEntity<FindPostRes> findPost(UUID postId, AuthenticatedUserReq auth);

    @Operation(summary = "게시글 전체 조회", description = "게시글을 전체 조회한다.")
    public ResponseEntity<Page<FindPostRes>> findPosts(String boardCategory, String region, int page);
}
