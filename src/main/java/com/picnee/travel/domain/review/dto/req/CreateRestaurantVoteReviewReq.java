package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateRestaurantVoteReviewReq extends BaseReviewReq {

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

    public ReviewVoteRestaurant toEntity(Review review, CreateRestaurantVoteReviewReq dto) {
        return ReviewVoteRestaurant.builder()
                .id(review.getId())
                .isKoreanMenu(dto.getIsKoreanMenu())
                .isSmoking(dto.getIsSmoking())
                .isReservation(dto.getIsReservation())
                .isOnlyReservation(dto.getIsOnlyReservation())
                .isKiosk(dto.getIsKiosk())
                .isCard(dto.getIsCard())
                .isCash(dto.getIsCash())
                .isLocalFlavor(dto.getIsLocalFlavor())
                .isCultural(dto.getIsCultural())
                .isAtmosphereGood(dto.getIsAtmosphereGood())
                .isSoloFriendly(dto.getIsSoloFriendly())
                .isGroupFriendly(dto.getIsGroupFriendly())
                .isServiceFriendly(dto.getIsServiceFriendly())
                .hasWaiting(dto.getHasWaiting())
                .isClean(dto.getIsClean())
                .isCostEffective(dto.getIsCostEffective())
                .isTasty(dto.getIsTasty())
                .build();
    }
}
