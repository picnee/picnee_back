package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteAccommodation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateAccommodationVoteReviewReq extends BaseReviewReq{

    @NotNull(message = "셀프 체크인/체크아웃 여부는 필수입니다.")
    private Boolean hasSelfCheckInOut;
    @NotNull(message = "24시간 프린트 운영 여부는 필수입니다.")
    private Boolean has24HrPrintService;
    @NotNull(message = "조식 제공 여부는 필수입니다.")
    private Boolean providesBreakfast;
    @NotNull(message = "짐 보관 서비스 운영 여부는 필수입니다.")
    private Boolean hasLuggageStorage;
    @NotNull(message = "부대시설 운영 여부는 필수입니다.")
    private Boolean hasFacilities;
    @NotNull(message = "대욕탕(온천) 운영 여부는 필수입니다.")
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

    public ReviewVoteAccommodation toEntity(Review review, CreateAccommodationVoteReviewReq dto) {
        return ReviewVoteAccommodation.builder()
                .id(review.getId())
                .hasSelfCheckInOut(dto.getHasSelfCheckInOut())
                .has24HrPrintService(dto.getHas24HrPrintService())
                .providesBreakfast(dto.getProvidesBreakfast())
                .hasLuggageStorage(dto.getHasLuggageStorage())
                .hasFacilities(dto.getHasFacilities())
                .hasLargeBath(dto.getHasLargeBath())
                .hasSpaciousRooms(dto.getHasSpaciousRooms())
                .hasCleanRooms(dto.getHasCleanRooms())
                .hasGoodRoomView(dto.getHasGoodRoomView())
                .hasHistoricalTradition(dto.getHasHistoricalTradition())
                .hasComfortableBeds(dto.getHasComfortableBeds())
                .hasGoodHeatingCooling(dto.getHasGoodHeatingCooling())
                .hasGoodSoundproofing(dto.getHasGoodSoundproofing())
                .hasDeliciousBreakfast(dto.getHasDeliciousBreakfast())
                .hasFriendlyService(dto.getHasFriendlyService())
                .isEasyPublicTransport(dto.getIsEasyPublicTransport())
                .build();
    }
}
