package com.picnee.travel.domain.review.dto.req;

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
public class UpdateRestaurantVoteReviewReq extends BaseReviewReq{

    @NotNull(message = "한국어 메뉴판 여부는 필수입니다.")
    private Boolean isKoreanMenu;
    @NotNull(message = "금연 식당 여부는 필수입니다.")
    private Boolean isSmoking;
    @NotNull(message = "키오스크 여부는 필수입니다.")
    private Boolean isKiosk;
    @NotNull(message = "카드 결제 여부는 필수입니다.")
    private Boolean isCard;
    @NotNull(message = "현금 결제 여부는 필수입니다.")
    private Boolean isCash;
    @NotNull(message = "예약제 운영 여부는 필수입니다.")
    private Boolean isOnlyReservation;
    @NotNull(message = "예약 가능 여부는 필수입니다.")
    private Boolean isReservation;
    private Boolean isTasty;
    private Boolean isLocalFlavor;
    private Boolean isCultural;
    private Boolean isAtmosphereGood;
    private Boolean isSoloFriendly;
    private Boolean isGroupFriendly;
    private Boolean isServiceFriendly;
    private Boolean hasWaiting;
    private Boolean isClean;
    private Boolean isCostEffective;
}
