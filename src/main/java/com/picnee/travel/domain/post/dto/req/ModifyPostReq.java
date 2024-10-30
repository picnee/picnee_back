package com.picnee.travel.domain.post.dto.req;

import com.picnee.travel.domain.board.entity.BoardCategory;
import com.picnee.travel.domain.board.entity.Region;
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
public class ModifyPostReq {

    @NotNull(message = "제목은 필수입니다.")
    private String title;
    @NotNull(message = "본문은 필수입니다.")
    private String content;
    @NotNull(message = "지역을 설정해주세요.")
    private Region region;
    @NotNull(message = "카테고리를 설정해주세요.")
    private BoardCategory boardCategory;
}
