package com.picnee.travel.domain.postComment.dto;

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
    private UUID commentParentId;

    public static PostComment toEntity(CreatePostCommentReq dto, User user, Post post) {
        return PostComment.builder()
                .user(user)
                .content(dto.content)
                .post(post)
                .build();
    }

    //TODO : 대댓글
    public static PostComment toEntityCoComment(CreatePostCommentReq dto, User user, Post post) {
        return PostComment.builder()
                .user(user)
                .content(dto.content)
                .post(post)
                .build();
    }

    public boolean existCommentParent(){
        return commentParentId != null;
    }
}
