package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.review.entity.RecommendationStatus;
import com.picnee.travel.domain.review.entity.Review;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public abstract class CreateVoteReviewReq {

    @NotNull(message = "실내 흡연 여부를 선택해주세요.")
    private boolean                 isSmoking;
    @NotNull(message = "카드 결제 여부를 선택해주세요.")
    private boolean                 isCard;
    @NotNull(message = "한국 종업원이 있는지 선택해주세요.")
    private boolean                 isKiosk;
    @NotNull(message = "한국 메뉴판이 있는지 선택해주세요.")
    private boolean                 isKoreanMenu;
    @NotNull(message = "추천 상태를 선택해주세요.")
    private RecommendationStatus    recommendationStatus;

    public abstract Review toEntity();
}