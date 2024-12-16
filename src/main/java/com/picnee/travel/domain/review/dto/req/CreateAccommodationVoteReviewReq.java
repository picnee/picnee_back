package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteAccommodation;
import com.picnee.travel.domain.review.entity.ReviewVoteTouristspot;
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
    @NotNull
    private UUID    reviewId;
    @NotNull
    private boolean isCleanlinessPositive;
    @NotNull
    private boolean isAccessibilityPositive;
    @NotNull
    private boolean isServicePositive;

    public ReviewVoteAccommodation toEntity(Review review) {
        return ReviewVoteAccommodation.builder()
                .id(review.getId())
                .isCleanlinessPositive(isCleanlinessPositive)
                .isAccessibilityPositive(isAccessibilityPositive)
                .isServicePositive(isServicePositive)
                .build();
    }
}
