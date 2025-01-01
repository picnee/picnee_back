package com.picnee.travel.domain.review.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GetRestaurantQueryRes {

    UUID getReviewId();
    String getGoodPoints();
    String getLowPoints();
    String getPlaceTips();
    Double getRating();
    LocalDateTime getCreatedAt();
    Boolean isKoreanMenu();
    Boolean isSmoking();
    Boolean isKiosk();
    Boolean isCard();
    Boolean isCash();
    Boolean isOnlyReservation();
    Boolean isReservation();
    Boolean isTasty();
    Boolean isLocalFlavor();
    Boolean isCultural();
    Boolean isAtmosphereGood();
    Boolean isSoloFriendly();
    Boolean isGroupFriendly();
    Boolean isServiceFriendly();
    Boolean hasWaiting();
    Boolean isClean();
    Boolean isCostEffective();

}
