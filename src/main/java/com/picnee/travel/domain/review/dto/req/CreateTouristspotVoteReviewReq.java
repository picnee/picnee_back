package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteTouristspot;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateTouristspotVoteReviewReq extends BaseReviewReq {
    @NotNull
    private UUID    reviewId;
    @NotNull
    private boolean isAccessibilityPositive;
    @NotNull
    private boolean isCrowded;
    @NotNull
    private boolean isExperiencePositive;

    public ReviewVoteTouristspot toEntity(Review review) {
        return ReviewVoteTouristspot.builder()
                .id(review.getId())
                .isAccessibilityPositive(isAccessibilityPositive)
                .isCrowded(isCrowded)
                .isExperiencePositive(isExperiencePositive)
                .build();
    }
}
