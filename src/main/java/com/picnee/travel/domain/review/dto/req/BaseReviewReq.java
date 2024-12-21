package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.review.entity.RecommendationStatus;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public abstract class BaseReviewReq {

    @NotNull(message = "실내 흡연 여부를 선택해주세요.")
    private boolean                 isSmoking;
    @NotNull(message = "카드 결제 여부를 선택해주세요.")
    private boolean                 isCard;
    @NotNull(message = "한국 종업원이 있는지 선택해주세요.")
    private boolean                 isKiosk;
    @NotNull(message = "한국 메뉴판이 있는지 선택해주세요.")
    private boolean                 isKoreanMenu;
    @NotNull(message = "추천 상태를 선택해주세요.")
    private RecommendationStatus    recommendationStatus;
    @NotNull
    private String    placeId;

    public Review toEntity(User user, Place place) {
        return Review.builder()
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