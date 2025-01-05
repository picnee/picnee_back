package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteTouristspot;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateTouristSpotVoteReviewReq extends BaseReviewReq {

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

    public  ReviewVoteTouristspot toEntity(Review review, CreateTouristSpotVoteReviewReq dto) {
        return ReviewVoteTouristspot.builder()
                .id(review.getId())
                .isPaidEntry(dto.getIsPaidEntry())
                .isReservationRequired(dto.getIsReservationRequired())
                .isKoreanGuideAvailable(dto.getIsKoreanGuideAvailable())
                .isBikeParkingAvailable(dto.getIsBikeParkingAvailable())
                .isCarParkingAvailable(dto.getIsCarParkingAvailable())
                .hasHistoricalTradition(dto.getHasHistoricalTradition())
                .hasManySights(dto.getHasManySights())
                .hasBeautifulNightView(dto.getHasBeautifulNightView())
                .isPhotoFriendly(dto.getIsPhotoFriendly())
                .hasGoodGuidance(dto.getHasGoodGuidance())
                .hasConvenientFacilities(dto.getHasConvenientFacilities())
                .hasExperiencePrograms(dto.getHasExperiencePrograms())
                .hasCleanRestrooms(dto.getHasCleanRestrooms())
                .isEasyPublicTransport(dto.getIsEasyPublicTransport())
                .isQuietAndPeaceful(dto.getIsQuietAndPeaceful())
                .build();

    }
}
