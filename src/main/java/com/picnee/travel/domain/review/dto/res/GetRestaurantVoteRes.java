package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetRestaurantVoteRes {
    private Boolean isKoreanMenu;
    private Boolean isSmoking;
    private Boolean isKiosk;
    private Boolean isCard;
    private Boolean isCash;
    private Boolean isOnlyReservation;
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

    static GetRestaurantVoteRes from(ReviewVoteRestaurant reviewVoteRestaurant) {
        return GetRestaurantVoteRes.builder()
                .isKoreanMenu(reviewVoteRestaurant.getIsKoreanMenu())
                .isSmoking(reviewVoteRestaurant.getIsSmoking())
                .isKiosk(reviewVoteRestaurant.getIsKiosk())
                .isCard(reviewVoteRestaurant.getIsCard())
                .isCash(reviewVoteRestaurant.getIsCash())
                .isOnlyReservation(reviewVoteRestaurant.getIsOnlyReservation())
                .isReservation(reviewVoteRestaurant.getIsReservation())
                .isTasty(reviewVoteRestaurant.getIsTasty())
                .isLocalFlavor(reviewVoteRestaurant.getIsLocalFlavor())
                .isCultural(reviewVoteRestaurant.getIsCultural())
                .isAtmosphereGood(reviewVoteRestaurant.getIsAtmosphereGood())
                .isSoloFriendly(reviewVoteRestaurant.getIsSoloFriendly())
                .isGroupFriendly(reviewVoteRestaurant.getIsGroupFriendly())
                .isServiceFriendly(reviewVoteRestaurant.getIsServiceFriendly())
                .hasWaiting(reviewVoteRestaurant.getHasWaiting())
                .isClean(reviewVoteRestaurant.getIsClean())
                .isCostEffective(reviewVoteRestaurant.getIsCostEffective())
                .build();
    }

}
