package com.picnee.travel.domain.postComment.dto.res;

import com.picnee.travel.domain.post.dto.res.FindPostRes;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.user.dto.res.UserRes;
import com.picnee.travel.domain.user.entity.User;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetPostCommentRes {

    private String content;
    private Long likes;
    private UserRes userRes;
    private List<GetChildrenCommentRes> replies;
    private LocalDateTime createdAt;

    public static List<GetPostCommentRes> from(List<PostComment> comments) {
        return comments.stream()
                .map(comment -> GetPostCommentRes.builder()
                        .content(comment.getContent())
                        .likes(comment.getLikes())
                        .userRes(UserRes.from(comment.getUser()))
                        .createdAt(comment.getCreatedAt())
                        .replies(GetChildrenCommentRes.from(comment.getChildren()))
                        .build())
                .collect(Collectors.toList());
    }
}
