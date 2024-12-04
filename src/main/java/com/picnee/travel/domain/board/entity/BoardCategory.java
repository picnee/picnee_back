package com.picnee.travel.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BoardCategory {
    RESTAURANT("음식점"),      // 음식점
    ACCOMMODATION("숙박"),    // 숙박
    TOURISTSPOT("관광지"),    // 관광지
    FREE_TALK("자유 토크"),   // 자유 토크
    WEATHER("날씨"),        // 날씨
    TRAFFIC("교통");       // 교통

    private final String category;

    BoardCategory(String category) {
        this.category = category;
    }

    @JsonValue
    public String getCategory() {
        return category;
    }

    @JsonCreator
    public static BoardCategory fromString(String category) {
        return switch (category) {
            case "음식점" -> BoardCategory.RESTAURANT;
            case "숙박" -> BoardCategory.ACCOMMODATION;
            case "관광지" -> BoardCategory.TOURISTSPOT;
            case "자유 토크" -> BoardCategory.FREE_TALK;
            case "날씨" -> BoardCategory.WEATHER;
            case "교통" -> BoardCategory.TRAFFIC;
            default -> null;
        };
    }
}
