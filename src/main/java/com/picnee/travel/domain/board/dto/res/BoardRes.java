package com.picnee.travel.domain.board.dto.res;

import com.picnee.travel.domain.board.entity.Board;
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
public class BoardRes {

    private UUID boardId;
    private String region;
    private String boardCategory;

    public static BoardRes from(Board board) {
        return BoardRes.builder()
                .boardId(board.getId())
                .region(board.getRegion().getDescription())
                .boardCategory(board.getBoardCategory().getCategory())
                .build();
    }
}
