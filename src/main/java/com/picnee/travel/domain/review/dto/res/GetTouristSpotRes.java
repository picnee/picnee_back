package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.Review;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetTouristSpotRes implements GetReviewRes{

    private GetReviewResImpl touristSpotRes;
    private GetTouristSptVoteRes touristSptVoteRes;
    private boolean loginStatus;

    public static GetTouristSpotRes of(Review review) {
        return GetTouristSpotRes.builder()
                .touristSpotRes(GetReviewResImpl.from(review))
                .touristSptVoteRes(GetTouristSptVoteRes.from(review.getReviewVoteTouristspot()))
                .build();
    }

    /**
     * 페이징 변환 메서드
     */
    public static Page<GetTouristSpotRes> getReviewsPaging(Page<Review> reviews) {
        List<GetTouristSpotRes> contents = reviews.getContent().stream()
                .map(GetTouristSpotRes::of)
                .collect(Collectors.toList());
        return new PageImpl<>(contents, reviews.getPageable(), reviews.getTotalElements());
    }

    /**
     * 인기 순위 음식점 리스트 변환
     */
    public static List<GetTouristSpotRes> list(List<Review> reviews) {
        return reviews.stream()
                .map(GetTouristSpotRes::of)
                .toList();
    }

    /**
     * 타입 변환
     */
    public static GetReviewRes toReviewRes(GetTouristSpotRes res) {
        return res;
    }
}
