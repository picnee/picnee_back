package com.picnee.travel.domain.post.dto.req;

import com.picnee.travel.domain.board.entity.Board;
import com.picnee.travel.domain.board.entity.BoardCategory;
import com.picnee.travel.domain.board.entity.Region;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreatePostReq {

    @NotNull(message = "제목은 필수입니다.")
    private String title;
    @NotNull(message = "본문은 필수입니다.")
    private String content;
    @NotNull(message = "지역을 설정해주세요.")
    private Region region;
    @NotNull(message = "카테고리를 설정해주세요.")
    private BoardCategory boardCategory;

    public static Post toEntity(CreatePostReq dto, Board board, User user) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .viewed(0L)
                .user(user)
                .board(board)
                .build();
    }
}
