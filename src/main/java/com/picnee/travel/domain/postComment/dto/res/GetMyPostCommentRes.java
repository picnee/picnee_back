package com.picnee.travel.domain.postComment.dto.res;

import com.picnee.travel.domain.post.dto.res.FindPostRes;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class GetMyPostCommentRes {

    private UUID commentId;
    private UUID postId;
    private String content;
    private Long likes;
    private UserRes userRes;
    private LocalDateTime createdAt;

    private static GetMyPostCommentRes from(PostComment postComment) {
        return GetMyPostCommentRes.builder()
                .commentId(postComment.getId())
                .postId(postComment.getPost().getId())
                .content(postComment.getContent())
                .likes(postComment.getLikes())
                .userRes(UserRes.from(postComment.getUser()))
                .createdAt(postComment.getCreatedAt())
                .build();
    }

    public static Page<GetMyPostCommentRes> paging(Page<PostComment> myComments) {
        List<GetMyPostCommentRes> contents = myComments.getContent().stream()
                .map(GetMyPostCommentRes::from)
                .collect(Collectors.toList());

        return new PageImpl<>(contents, myComments.getPageable(), myComments.getTotalElements());
    }
}
