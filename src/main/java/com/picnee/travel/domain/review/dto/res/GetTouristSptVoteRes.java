package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.ReviewVoteTouristspot;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetTouristSptVoteRes {

    private Boolean isPaidEntry;
    private Boolean isReservationRequired;
    private Boolean isKoreanGuideAvailable;
    private Boolean isBikeParkingAvailable;
    private Boolean isCarParkingAvailable;
    private Boolean hasHistoricalTradition;
    private Boolean hasManySights;
    private Boolean hasBeautifulNightView;
    private Boolean isPhotoFriendly;
    private Boolean hasGoodGuidance;
    private Boolean hasConvenientFacilities;
    private Boolean hasExperiencePrograms;
    private Boolean hasCleanRestrooms;
    private Boolean isEasyPublicTransport;
    private Boolean isQuietAndPeaceful;

    static GetTouristSptVoteRes from(ReviewVoteTouristspot reviewVoteTouristspot) {
        return GetTouristSptVoteRes.builder()
                .isPaidEntry(reviewVoteTouristspot.getIsPaidEntry())
                .isReservationRequired(reviewVoteTouristspot.getIsReservationRequired())
                .isKoreanGuideAvailable(reviewVoteTouristspot.getIsKoreanGuideAvailable())
                .isBikeParkingAvailable(reviewVoteTouristspot.getIsBikeParkingAvailable())
                .isCarParkingAvailable(reviewVoteTouristspot.getIsCarParkingAvailable())
                .hasHistoricalTradition(reviewVoteTouristspot.getHasHistoricalTradition())
                .hasManySights(reviewVoteTouristspot.getHasManySights())
                .hasBeautifulNightView(reviewVoteTouristspot.getHasBeautifulNightView())
                .isPhotoFriendly(reviewVoteTouristspot.getIsPhotoFriendly())
                .hasGoodGuidance(reviewVoteTouristspot.getHasGoodGuidance())
                .hasConvenientFacilities(reviewVoteTouristspot.getHasConvenientFacilities())
                .hasExperiencePrograms(reviewVoteTouristspot.getHasExperiencePrograms())
                .hasCleanRestrooms(reviewVoteTouristspot.getHasCleanRestrooms())
                .isEasyPublicTransport(reviewVoteTouristspot.getIsEasyPublicTransport())
                .isQuietAndPeaceful(reviewVoteTouristspot.getIsQuietAndPeaceful())
                .build();
    }
}
