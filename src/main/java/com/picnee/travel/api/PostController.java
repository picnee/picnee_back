package com.picnee.travel.api;

import com.picnee.travel.api.in.PostApi;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.dto.req.ModifyPostReq;
import com.picnee.travel.domain.post.dto.res.FindPostRes;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody CreatePostReq dto,
                                           @AuthenticatedUser AuthenticatedUserReq auth) {
        Post post = postService.create(dto, auth);
        return ResponseEntity.status(CREATED).body(post.getId().toString());
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable("postId") UUID postId,
                                           @RequestBody ModifyPostReq dto,
                                           @AuthenticatedUser AuthenticatedUserReq auth) {
        Post post = postService.update(postId, dto, auth);
        return ResponseEntity.status(NO_CONTENT).body(post.getId().toString());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") UUID postId,
                                           @AuthenticatedUser AuthenticatedUserReq auth) {
        postService.delete(postId, auth);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FindPostRes> findPost(@PathVariable("postId") UUID postId,
                                                @AuthenticatedUser AuthenticatedUserReq auth) {
        FindPostRes findPostRes = postService.find(postId, auth);
        return ResponseEntity.status(OK).body(findPostRes);
    }

    @GetMapping
    public ResponseEntity<Page<FindPostRes>> findPosts(@RequestParam(name = "boardCategory", required = false) String boardCategory,
                                                       @RequestParam(name = "region", required = false) String region,
                                                       @RequestParam(name = "sort", required = false, defaultValue = "new") String sort,
                                                       @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<FindPostRes> posts = postService.findPosts(boardCategory, region, sort, page);
        return ResponseEntity.status(OK).body(posts);
    }
}
