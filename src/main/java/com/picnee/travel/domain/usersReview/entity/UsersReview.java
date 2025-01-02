package com.picnee.travel.domain.usersReview.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import com.picnee.travel.domain.base.entity.SoftDeleteBaseEntity;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.usersReview.dto.req.EvaluateReviewReq;
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
@Table(name = "users_review")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UsersReview extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "user_review_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "good_and_bad")
    private Boolean goodAndBad;
    @Column(name = "is_liked")
    private Boolean isLiked;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    /**
     * 좋아요
     */
    public void like() {
        this.isLiked = true;
    }

    /**
     * 좋아요 취소
     */
    public void dislike() {
        this.isLiked = false;
    }

    /**
     * 리뷰 평가
     */
    public void updateEvaluation(EvaluateReviewReq dto) {
        this.goodAndBad = dto.isGoodAndBad();
    }
}
