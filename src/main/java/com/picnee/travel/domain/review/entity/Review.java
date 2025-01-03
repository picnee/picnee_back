package com.picnee.travel.domain.review.entity;

import com.picnee.travel.domain.base.entity.SoftDeleteBaseEntity;
import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.entity.PlaceType;
import com.picnee.travel.domain.review.dto.req.BaseReviewReq;
import com.picnee.travel.domain.review.dto.req.UpdateRestaurantVoteReviewReq;
import com.picnee.travel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Getter
@Entity
@Table(name = "review")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Review extends SoftDeleteBaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "review_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "good_points")
    private String goodPoints;
    @Column(name = "low_points")
    private String lowPoints;
    @Column(name = "place_tips")
    private String placeTips;
    @Column(name = "likes")
    private Long likes;
    @Column(name = "rating")
    private Double rating;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private ReviewVoteRestaurant reviewVoteRestaurant;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private ReviewVoteAccommodation reviewVoteAccommodation;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private ReviewVoteTouristspot reviewVoteTouristspot;


    /**
     * 리뷰 삭제
     */
    public void softDelete() {
        super.delete();
    }


    /**
     * 음식점 리뷰 수정
     */
    public void update(BaseReviewReq dto) {
        this.goodPoints = dto.getGoodPoints() == null ? this.goodPoints : dto.getGoodPoints();
        this.lowPoints = dto.getLowPoints() == null ? this.lowPoints : dto.getLowPoints();
        this.placeTips = dto.getPlaceTips() == null ? this.placeTips : dto.getPlaceTips();
        this.rating = dto.getRating() == null ? this.rating : dto.getRating();
    }

    /**
     * 리뷰 좋아요
     */
    public void addLike() {
        this.likes++;
    }

    /**
     * 리뷰 좋아요 취소
     */
    public void deleteLike() {
        this.likes--;
    }
}
