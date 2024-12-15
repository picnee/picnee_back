package com.picnee.travel.domain.review.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RecommendationStatus {

    BEST,       // 최고
    GOOD,       // 좋음
    NOT_BAD     // 나쁘지 않음
}
