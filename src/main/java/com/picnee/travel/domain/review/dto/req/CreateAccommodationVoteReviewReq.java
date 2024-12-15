package com.picnee.travel.domain.review.dto.req;

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
public class CreateAccommodationVoteReviewReq {
    @NotNull
    private UUID    reviewId;
    @NotNull
    private boolean isCleanlinessPositive;
    @NotNull
    private boolean accessibilityPositive;
    @NotNull
    private boolean servicePositive;
}
