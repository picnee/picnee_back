package com.picnee.travel.domain.postComment.dto.res;

import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetChildrenCommentRes {

    private String content;
    private Long likes;
    private UserRes userRes;
    private LocalDateTime createdAt;

    public static List<GetChildrenCommentRes> from(List<PostComment> replies) {
        return replies.stream()
                .map(reply -> GetChildrenCommentRes.builder()
                        .content(reply.getContent())
                        .likes(reply.getLikes())
                        .userRes(UserRes.from(reply.getUser()))
                        .createdAt(reply.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
