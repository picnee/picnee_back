package com.picnee.travel.api.in;

import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "posts", description = "post API")
public interface PostApi {

    @Operation(summary = "게시글 생성", description = "게시글을 생성한다.")
    public ResponseEntity<Void> createPost(CreatePostReq dto, AuthenticatedUserReq auth);
}
