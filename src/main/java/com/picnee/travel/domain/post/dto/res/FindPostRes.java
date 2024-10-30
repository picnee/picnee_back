package com.picnee.travel.domain.post.dto.res;

import com.picnee.travel.domain.board.dto.res.BoardRes;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class FindPostRes {

    private UUID postId;
    private String title;
    private String content;
    private Long viewed;
    private Long likes;
    private BoardRes boardRes;
    private UserRes userRes;
    private LocalDateTime createdAt;

    public static FindPostRes from(Post post) {
        return FindPostRes.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewed(post.getViewed())
                .likes(post.getLikes())
                .boardRes(BoardRes.from(post.getBoard()))
                .userRes(UserRes.from(post.getUser()))
                .createdAt(post.getCreatedAt())
                .build();

    }
}
