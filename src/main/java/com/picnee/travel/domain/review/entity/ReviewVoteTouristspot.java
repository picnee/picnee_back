package com.picnee.travel.domain.review.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import com.picnee.travel.domain.review.dto.req.UpdateTouristSpotVoteReviewReq;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Getter
@Entity
@Table(name = "review_vote_touristspot")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ReviewVoteTouristspot {

    @Id
    @EqualsAndHashCode.Include
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "review_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    // 기본 정보
    @Column(name = "is_paid_entry", nullable = false)
    private Boolean isPaidEntry;
    @Column(name = "is_reservation_required", nullable = false)
    private Boolean isReservationRequired;
    @Column(name = "is_korean_guide_available", nullable = false)
    private Boolean isKoreanGuideAvailable;
    @Column(name = "is_bike_parking_available", nullable = false)
    private Boolean isBikeParkingAvailable;
    @Column(name = "is_car_parking_available", nullable = false)
    private Boolean isCarParkingAvailable;

    // 장소 특징
    @Column(name = "has_historical_tradition")
    private Boolean hasHistoricalTradition;
    @Column(name = "has_many_sights")
    private Boolean hasManySights;
    @Column(name = "has_beautiful_night_view")
    private Boolean hasBeautifulNightView;
    @Column(name = "is_photo_friendly")
    private Boolean isPhotoFriendly;
    @Column(name = "has_good_guidance")
    private Boolean hasGoodGuidance;
    @Column(name = "has_convenient_facilities")
    private Boolean hasConvenientFacilities;
    @Column(name = "has_experience_programs")
    private Boolean hasExperiencePrograms;
    @Column(name = "has_clean_restrooms")
    private Boolean hasCleanRestrooms;
    @Column(name = "is_easy_public_transport")
    private Boolean isEasyPublicTransport;
    @Column(name = "is_quiet_and_peaceful")
    private Boolean isQuietAndPeaceful;

    /**
     * 관광지 리뷰 수정
     */
    public void update(UpdateTouristSpotVoteReviewReq dto) {
        this.isPaidEntry = dto.getIsPaidEntry() == null ? this.isPaidEntry : dto.getIsPaidEntry();
        this.isReservationRequired = dto.getIsReservationRequired() == null ? this.isReservationRequired : dto.getIsReservationRequired();
        this.isKoreanGuideAvailable = dto.getIsKoreanGuideAvailable() == null ? this.isKoreanGuideAvailable : dto.getIsKoreanGuideAvailable();
        this.isBikeParkingAvailable = dto.getIsBikeParkingAvailable() == null ? this.isBikeParkingAvailable : dto.getIsBikeParkingAvailable();
        this.isCarParkingAvailable = dto.getIsCarParkingAvailable() == null ? this.isCarParkingAvailable : dto.getIsCarParkingAvailable();
        this.hasHistoricalTradition = dto.getHasHistoricalTradition() == null ? this.hasHistoricalTradition : dto.getHasHistoricalTradition();
        this.hasManySights = dto.getHasManySights() == null ? this.hasManySights : dto.getHasManySights();
        this.hasBeautifulNightView = dto.getHasBeautifulNightView() == null ? this.hasBeautifulNightView : dto.getHasBeautifulNightView();
        this.isPhotoFriendly = dto.getIsPhotoFriendly() == null ? this.isPhotoFriendly : dto.getIsPhotoFriendly();
        this.hasGoodGuidance = dto.getHasGoodGuidance() == null ? this.hasGoodGuidance : dto.getHasGoodGuidance();
        this.hasConvenientFacilities = dto.getHasConvenientFacilities() == null ? this.hasConvenientFacilities : dto.getHasConvenientFacilities();
        this.hasExperiencePrograms = dto.getHasExperiencePrograms() == null ? this.hasExperiencePrograms : dto.getHasExperiencePrograms();
        this.hasCleanRestrooms = dto.getHasCleanRestrooms() == null ? this.hasCleanRestrooms : dto.getHasCleanRestrooms();
        this.isEasyPublicTransport = dto.getIsEasyPublicTransport() == null ? this.isEasyPublicTransport : dto.getIsEasyPublicTransport();
        this.isQuietAndPeaceful = dto.getIsQuietAndPeaceful() == null ? this.isQuietAndPeaceful : dto.getIsQuietAndPeaceful();
    }
}
