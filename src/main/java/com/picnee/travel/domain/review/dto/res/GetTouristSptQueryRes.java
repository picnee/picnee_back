package com.picnee.travel.domain.review.dto.res;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GetTouristSptQueryRes {

    UUID getReviewId();
    String getGoodPoints();
    String getLowPoints();
    String getPlaceTips();
    Double getRating();
    LocalDateTime getCreatedAt();
    Boolean isPaidEntry();
    Boolean isReservationRequired();
    Boolean isKoreanGuideAvailable();
    Boolean isBikeParkingAvailable();
    Boolean isCarParkingAvailable();
    Boolean hasHistoricalTradition();
    Boolean hasManySights();
    Boolean hasBeautifulNightView();
    Boolean isPhotoFriendly();
    Boolean hasGoodGuidance();
    Boolean hasConvenientFacilities();
    Boolean hasExperiencePrograms();
    Boolean hasCleanRestrooms();
    Boolean isEasyPublicTransport();
    Boolean isQuietAndPeaceful();
}
