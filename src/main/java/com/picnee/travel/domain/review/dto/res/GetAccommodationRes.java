package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.Review;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetAccommodationRes implements GetReviewRes {

    private GetReviewResImpl restaurantRes;
    private GetAccommodationVoteRes accommodationVoteRes;

    /**
     * 단건 조회
     */
    public static GetAccommodationRes of(Review review) {
        return GetAccommodationRes.builder()
                .restaurantRes(GetReviewResImpl.from(review))
                .accommodationVoteRes(GetAccommodationVoteRes.from(review.getReviewVoteAccommodation()))
                .build();
    }

    /**
     * 페이징 변환 메서드
     */
    public static Page<GetAccommodationRes> getReviewsPaging(Page<Review> reviews) {
        List<GetAccommodationRes> contents = reviews.getContent().stream()
                .map(GetAccommodationRes::of)
                .collect(Collectors.toList());
        return new PageImpl<>(contents, reviews.getPageable(), reviews.getTotalElements());
    }

    /**
     * 인기 순위 조회
     */
    public static List<GetAccommodationRes> list(List<Review> reviews) {
        return reviews.stream()
                .map(GetAccommodationRes::of)
                .toList();
        }

    public static GetReviewRes toReviewRes(GetAccommodationRes res) {
        return res;
    }
}
