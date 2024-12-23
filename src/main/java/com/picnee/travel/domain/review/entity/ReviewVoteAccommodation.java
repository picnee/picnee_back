package com.picnee.travel.domain.review.entity;

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
import org.hibernate.type.SqlTypes;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "review_vote_accommodation")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ReviewVoteAccommodation {

    @Id
    @EqualsAndHashCode.Include
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "review_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    // 기본 정보
    @Column(name = "has_self_check_in_out", nullable = false)
    private Boolean hasSelfCheckInOut;
    @Column(name = "has_24hr_print_service", nullable = false)
    private Boolean has24HrPrintService;
    @Column(name = "provides_breakfast", nullable = false)
    private Boolean providesBreakfast;
    @Column(name = "has_luggage_storage", nullable = false)
    private Boolean hasLuggageStorage;
    @Column(name = "has_facilities", nullable = false)
    private Boolean hasFacilities;
    @Column(name = "has_large_bath", nullable = false)
    private Boolean hasLargeBath;

    // 객실 특징
    @Column(name = "has_spacious_rooms")
    private Boolean hasSpaciousRooms;
    @Column(name = "has_clean_rooms")
    private Boolean hasCleanRooms;
    @Column(name = "has_good_room_view")
    private Boolean hasGoodRoomView;
    @Column(name = "has_historical_tradition")
    private Boolean hasHistoricalTradition;
    @Column(name = "has_comfortable_beds")
    private Boolean hasComfortableBeds;
    @Column(name = "has_good_heating_cooling")
    private Boolean hasGoodHeatingCooling;
    @Column(name = "has_good_soundproofing")
    private Boolean hasGoodSoundproofing;
    @Column(name = "has_delicious_breakfast")
    private Boolean hasDeliciousBreakfast;
    @Column(name = "has_friendly_service")
    private Boolean hasFriendlyService;
    @Column(name = "is_easy_public_transport")
    private Boolean isEasyPublicTransport;
}
