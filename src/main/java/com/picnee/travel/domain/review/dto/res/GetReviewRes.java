package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.place.dto.res.PlaceRes;
import com.picnee.travel.domain.postComment.dto.res.GetMyPostCommentRes;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.review.entity.RecommendationStatus;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class GetReviewRes {

    private UUID reviewId;
    private PlaceRes placeRes;
    private String title;
    private String content;
    private boolean isSmoking;
    private boolean isKiosk;
    private boolean isCard;
    private boolean isKoreanMenu;
    private boolean isVoteReview;
    private RecommendationStatus recommendationStatus;
    private UserRes userRes;
    private boolean loginStatus;

    /**
     * 단건 조회
     */
    public static GetReviewRes of(Review review, AuthenticatedUserReq auth) {
        return GetReviewRes.builder()
                .reviewId(review.getId())
                .placeRes(PlaceRes.from(review.getPlace()))
                .title(review.getTitle())
                .content(review.getContent())
                .isSmoking(review.isSmoking())
                .isCard(review.isCard())
                .isKiosk(review.isKiosk())
                .isKoreanMenu(review.isKoreanMenu())
                .isVoteReview(review.isVoteReview())
                .recommendationStatus(review.getRecommendationStatus())
                .userRes(UserRes.from(review.getUser()))
                .loginStatus(auth != null)
                .build();
    }

    /**
     * 페이징
     */
    public static GetReviewRes from(Review review) {
        return GetReviewRes.builder()
                .reviewId(review.getId())
                .placeRes(PlaceRes.from(review.getPlace()))
                .title(review.getTitle())
                .content(review.getContent())
                .recommendationStatus(review.getRecommendationStatus())
                .userRes(UserRes.from(review.getUser()))
                .build();
    }

    public static Page<GetReviewRes> paging(Page<Review> reviews) {
        List<GetReviewRes> contents = reviews.getContent().stream()
                .map(GetReviewRes::from)
                .collect(Collectors.toList());

        return new PageImpl<>(contents, reviews.getPageable(), reviews.getTotalElements());
    }
}
