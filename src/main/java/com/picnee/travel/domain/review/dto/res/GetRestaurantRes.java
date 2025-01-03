package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetRestaurantRes implements GetReviewRes{

    private GetReviewResImpl restaurantRes;
    private GetRestaurantVoteRes restaurantVoteRes;

    /**
     * 단건 조회
     */
    public static GetRestaurantRes of(Review review) {
        return GetRestaurantRes.builder()
                .restaurantRes(GetReviewResImpl.from(review))
                .restaurantVoteRes(GetRestaurantVoteRes.from(review.getReviewVoteRestaurant()))
                .build();
    }

    /**
     * 페이징 변환 메서드
     */
    public static Page<GetRestaurantRes> getReviewsPaging(Page<Review> reviews) {
        List<GetRestaurantRes> contents = reviews.getContent().stream()
                .map(GetRestaurantRes::of)
                .collect(Collectors.toList());
        return new PageImpl<>(contents, reviews.getPageable(), reviews.getTotalElements());
    }

    /**
     * 인기 순위 음식점 리스트 변환
     */
    public static List<GetRestaurantRes>  list(List<Review> reviews) {
        return reviews.stream()
                .map(GetRestaurantRes::of)
                .toList();
    }

    /**
     * 타입 변환
     */
    public static GetReviewRes toReviewRes(GetRestaurantRes res) {
        return res;
    }
}
