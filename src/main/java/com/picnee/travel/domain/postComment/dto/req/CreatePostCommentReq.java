package com.picnee.travel.domain.postComment.dto.req;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreatePostCommentReq {

    @NotNull(message = "본문은 필수입니다.")
    private String content;

    public static PostComment toEntity(CreatePostCommentReq dto, User user, Post post) {
        return PostComment.builder()
                .user(user)
                .content(dto.content)
                .likes(0L)
                .post(post)
                .build();
    }

    public static PostComment toEntityCoComment(Post post, PostComment postComment, User user, CreatePostCommentReq dto) {
        return PostComment.builder()
                .user(user)
                .content(dto.content)
                .likes(0L)
                .commentParent(postComment)
                .post(post)
                .build();
    }
}
