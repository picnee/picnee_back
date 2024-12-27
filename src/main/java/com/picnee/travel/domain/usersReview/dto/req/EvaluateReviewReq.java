package com.picnee.travel.domain.usersReview.dto.req;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.usersReview.entity.UsersReview;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EvaluateReviewReq {

    private boolean goodAndBad;

    public static UsersReview toEntity(EvaluateReviewReq dto, Review review, User user) {
        return UsersReview.builder()
                .goodAndBad(dto.isGoodAndBad())
                .review(review)
                .user(user)
                .build();
    }
}
