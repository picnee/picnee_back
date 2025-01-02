package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.ReviewVoteAccommodation;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetAccommodationVoteRes {

    private Boolean hasSelfCheckInOut;
    private Boolean has24HrPrintService;
    private Boolean providesBreakfast;
    private Boolean hasLuggageStorage;
    private Boolean hasFacilities;
    private Boolean hasLargeBath;
    private Boolean hasSpaciousRooms;
    private Boolean hasCleanRooms;
    private Boolean hasGoodRoomView;
    private Boolean hasHistoricalTradition;
    private Boolean hasComfortableBeds;
    private Boolean hasGoodHeatingCooling;
    private Boolean hasGoodSoundproofing;
    private Boolean hasDeliciousBreakfast;
    private Boolean hasFriendlyService;
    private Boolean isEasyPublicTransport;


    static GetAccommodationVoteRes from(ReviewVoteAccommodation reviewVoteAccommodation) {
        return GetAccommodationVoteRes.builder()
                .hasSelfCheckInOut(reviewVoteAccommodation.getHasSelfCheckInOut())
                .has24HrPrintService(reviewVoteAccommodation.getHas24HrPrintService())
                .providesBreakfast(reviewVoteAccommodation.getProvidesBreakfast())
                .hasLuggageStorage(reviewVoteAccommodation.getHasLuggageStorage())
                .hasFacilities(reviewVoteAccommodation.getHasFacilities())
                .hasLargeBath(reviewVoteAccommodation.getHasLargeBath())
                .hasSpaciousRooms(reviewVoteAccommodation.getHasSpaciousRooms())
                .hasCleanRooms(reviewVoteAccommodation.getHasCleanRooms())
                .hasGoodRoomView(reviewVoteAccommodation.getHasGoodRoomView())
                .hasHistoricalTradition(reviewVoteAccommodation.getHasHistoricalTradition())
                .hasComfortableBeds(reviewVoteAccommodation.getHasComfortableBeds())
                .hasGoodHeatingCooling(reviewVoteAccommodation.getHasGoodHeatingCooling())
                .hasGoodSoundproofing(reviewVoteAccommodation.getHasGoodSoundproofing())
                .hasDeliciousBreakfast(reviewVoteAccommodation.getHasDeliciousBreakfast())
                .hasFriendlyService(reviewVoteAccommodation.getHasFriendlyService())
                .isEasyPublicTransport(reviewVoteAccommodation.getIsEasyPublicTransport())
                .build();
    }


}
