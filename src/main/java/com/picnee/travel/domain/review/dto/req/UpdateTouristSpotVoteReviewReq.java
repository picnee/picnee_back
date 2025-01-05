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
public class UpdateTouristSpotVoteReviewReq extends BaseReviewReq{

    @NotNull(message = "유료 입장 여부는 필수입니다.")
    private Boolean isPaidEntry;
    @NotNull(message = "사전 예약 여부는 필수입니다.")
    private Boolean isReservationRequired;
    @NotNull(message = "한국어 가이드 제공 여부는 필수입니다.")
    private Boolean isKoreanGuideAvailable;
    @NotNull(message = "자전거 주차 가능 여부는 필수입니다.")
    private Boolean isBikeParkingAvailable;
    @NotNull(message = "자동차 주차 가능 여부는 필수입니다.")
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
}
