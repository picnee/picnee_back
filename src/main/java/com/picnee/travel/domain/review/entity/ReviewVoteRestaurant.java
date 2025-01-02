package com.picnee.travel.domain.review.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import com.picnee.travel.domain.review.dto.req.UpdateRestaurantVoteReviewReq;
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
@Table(name = "review_vote_restaurant")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ReviewVoteRestaurant {
    @Id
    @EqualsAndHashCode.Include
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "review_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "is_korean_menu")
    private Boolean isKoreanMenu;
    @Column(name = "is_smoking")
    private Boolean isSmoking;
    @Column(name = "is_kiosk")
    private Boolean isKiosk;
    @Column(name = "is_card")
    private Boolean isCard;
    @Column(name = "is_cash")
    private Boolean isCash;
    @Column(name = "is_only_reservation")
    private Boolean isOnlyReservation;
    @Column(name = "is_reservation")
    private Boolean isReservation;
    @Column(name = "is_tasty")
    private Boolean isTasty;
    @Column(name = "is_local_flavor")
    private Boolean isLocalFlavor;
    @Column(name = "is_cultural")
    private Boolean isCultural;
    @Column(name = "is_atmosphere_good")
    private Boolean isAtmosphereGood;
    @Column(name = "is_solo_friendly")
    private Boolean isSoloFriendly;
    @Column(name = "is_group_friendly")
    private Boolean isGroupFriendly;
    @Column(name = "is_service_friendly")
    private Boolean isServiceFriendly;
    @Column(name = "has_waiting")
    private Boolean hasWaiting;
    @Column(name = "is_clean")
    private Boolean isClean;
    @Column(name = "is_cost_effective")
    private Boolean isCostEffective;

    /**
     * 음식점 투표 리뷰 수정
     */
    public void update(UpdateRestaurantVoteReviewReq dto) {
        this.isKoreanMenu = dto.getIsKoreanMenu() == null ? this.isKoreanMenu : dto.getIsKoreanMenu();
        this.isSmoking = dto.getIsSmoking() == null ? this.isSmoking : dto.getIsSmoking();
        this.isKiosk = dto.getIsKiosk() == null ? this.isKiosk : dto.getIsKiosk();
        this.isCard = dto.getIsCard() == null ? this.isCard : dto.getIsCard();
        this.isCash = dto.getIsCash() == null ? this.isCash : dto.getIsCash();
        this.isOnlyReservation = dto.getIsOnlyReservation() == null ? this.isOnlyReservation : dto.getIsOnlyReservation();
        this.isReservation = dto.getIsReservation() == null ? this.isReservation : dto.getIsReservation();
        this.isTasty = dto.getIsTasty() == null ? this.isTasty : dto.getIsTasty();
        this.isLocalFlavor = dto.getIsLocalFlavor() == null ? this.isLocalFlavor : dto.getIsLocalFlavor();
        this.isCultural = dto.getIsCultural() == null ? this.isCultural: dto.getIsCultural();
        this.isAtmosphereGood = dto.getIsAtmosphereGood() == null ? this.isAtmosphereGood : dto.getIsAtmosphereGood();
        this.isSoloFriendly = dto.getIsSoloFriendly() == null ? this.isSoloFriendly : dto.getIsSoloFriendly();
        this.isGroupFriendly = dto.getIsGroupFriendly() == null ? this.isGroupFriendly : dto.getIsGroupFriendly();
        this.isServiceFriendly = dto.getIsServiceFriendly() == null ? this.isServiceFriendly : dto.getIsServiceFriendly();
        this.hasWaiting = dto.getHasWaiting() == null ? this.hasWaiting : dto.getHasWaiting();
        this.isClean = dto.getIsClean() == null ? this.isClean : dto.getIsClean();
        this.isCostEffective = dto.getIsCostEffective() == null ? this.isCostEffective : dto.getIsCostEffective();
    }
}
