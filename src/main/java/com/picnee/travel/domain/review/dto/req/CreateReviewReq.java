package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateReviewReq extends BaseReviewReq {
    @NotNull
    private String title;
    @NotNull
    private String content;

    @Override
    public Review toEntity(User user, Place place) {
        return Review.builder()
                .title(title)
                .content(content)
                .isVoteReview(true)
                .isCard(isCard())
                .isKiosk(isKiosk())
                .isSmoking(isSmoking())
                .isKoreanMenu(isKoreanMenu())
                .recommendationStatus(getRecommendationStatus())
                .user(user)
                .place(place)
                .build();
    }
}
