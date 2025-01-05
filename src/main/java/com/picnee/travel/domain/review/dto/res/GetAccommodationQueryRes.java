package com.picnee.travel.domain.review.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GetAccommodationQueryRes {

    UUID getReviewId();
    String getGoodPoints();
    String getLowPoints();
    String getPlaceTips();
    Double getRating();
    LocalDateTime getCreatedAt();
    Boolean getHasSelfCheckInOut();
    Boolean getHas24HrPrintService();
    Boolean getProvidesBreakfast();
    Boolean getHasLuggageStorage();
    Boolean getHasFacilities();
    Boolean getHasLargeBath();
    Boolean getHasSpaciousRooms();
    Boolean getHasCleanRooms();
    Boolean getHasGoodRoomView();
    Boolean getHasHistoricalTradition();
    Boolean getHasComfortableBeds();
    Boolean getHasGoodHeatingCooling();
    Boolean getHasGoodSoundproofing();
    Boolean getHasDeliciousBreakfast();
    Boolean getHasFriendlyService();
    Boolean getIsEasyPublicTransport();
    Integer getWeightedScore();


}
