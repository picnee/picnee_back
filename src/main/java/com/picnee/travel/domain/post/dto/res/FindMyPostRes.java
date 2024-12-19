package com.picnee.travel.domain.post.dto.res;

import com.picnee.travel.domain.board.dto.res.BoardRes;
import com.picnee.travel.domain.post.entity.Post;
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
public class FindMyPostRes {

    private UUID postId;
    private String title;
    private BoardRes boardRes;

    public static FindMyPostRes from(Post post) {
        return FindMyPostRes.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .boardRes(BoardRes.from(post.getBoard()))
                .build();

    }
}
