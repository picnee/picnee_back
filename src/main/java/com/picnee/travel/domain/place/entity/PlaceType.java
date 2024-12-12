package com.picnee.travel.domain.place.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.picnee.travel.domain.board.entity.Region;
import com.picnee.travel.domain.board.exception.NotFoundBoardException;
import com.picnee.travel.domain.place.exception.NotFoundCategoryException;
import com.picnee.travel.global.exception.ErrorCode;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PlaceType {
    RESTAURANT("음식점"),     // 음식점
    LODGING("숙소"),  // 숙소
    TOURISTSPOT("관광지");     // 관광지

    private final String category;

    PlaceType(String category) {
        this.category = category;
    }

    @JsonValue
    public String getCategory() {
        return category;
    }

    @JsonCreator
    public static PlaceType fromString(String category) {
        return switch (category) {
            case "음식점" -> PlaceType.RESTAURANT;
            case "숙소" -> PlaceType.LODGING;
            case "관광지" -> PlaceType.TOURISTSPOT;
            default -> throw new NotFoundCategoryException(ErrorCode.NOT_FOUND_BOARD_CATEGORY_EXCEPTION);
        };
    }
}
