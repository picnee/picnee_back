package com.picnee.travel.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.picnee.travel.domain.board.exception.NotFoundBoardException;
import com.picnee.travel.global.exception.ErrorCode;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Region {
    OSAKA("오사카"),          // 오사카
    KYOTO("교토"),           // 교토
    KOBE("고베"),            // 고베
    FUKUOKA("후쿠오카"),      // 후쿠오카
    TOKYO("도쿄"),           // 도쿄
    SAPPORO("삿포로");        // 삿포로

    private final String description;

    Region(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Region fromString(String category) {
        return switch (category) {
            case "오사카" -> Region.OSAKA;
            case "교토" -> Region.KYOTO;
            case "고베" -> Region.KOBE;
            case "후쿠오카" -> Region.FUKUOKA;
            case "도쿄" -> Region.TOKYO;
            case "삿포로" -> Region.SAPPORO;
            default -> throw new NotFoundBoardException(ErrorCode.NOT_FOUND_BOARD_REGION_EXCEPTION);
        };
    }
}
