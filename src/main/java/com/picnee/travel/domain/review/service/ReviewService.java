package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.entity.PlaceType;
import com.picnee.travel.domain.place.service.PlaceService;
import com.picnee.travel.domain.post.exception.NotFoundPostException;
import com.picnee.travel.domain.review.dto.req.*;
import com.picnee.travel.domain.review.dto.res.GetRestaurantRes;
import com.picnee.travel.domain.review.dto.res.GetReviewRes;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.exception.NotFoundReviewException;
import com.picnee.travel.domain.review.exception.NotReviewAuthorException;
import com.picnee.travel.domain.review.repository.ReviewRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final PlaceService placeService;
    private final UserService userService;
    private final ReviewVoteRestaurantService reviewVoteRestaurantService;
    private final ReviewVoteTouristSpotService reviewVoteTouristSpotService;
    private final ReviewVoteAccommodationService reviewVoteAccommodationService;

    /**
     * 음식점 리뷰 생성
     */
    @Transactional
    public Review createRestaurantReview(CreateRestaurantVoteReviewReq dto, String placeId, AuthenticatedUserReq auth) {
        User    user        = userService.findByEmail(auth.getEmail());
        Place   place       = placeService.findById(placeId);
        // 음식점 리뷰 생성
        Review review = reviewRepository.save(dto.toEntity(dto, user, place));
        // 투표 식당 여부 생성
        reviewVoteRestaurantService.createRestaurantReview(review, dto);
        return review;
    }

    /**
     * 관광지 리뷰 생성
     */
    @Transactional
    public Review createTouristSpotReview(CreateTouristSpotVoteReviewReq dto, String placeId, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        Place place = placeService.findById(placeId);

        // 관광지 리뷰 생성
        Review review = reviewRepository.save(dto.toEntity(dto, user, place));
        // 투표 관광지 여부 생성
        reviewVoteTouristSpotService.createTouristSpotReview(review, dto);
        return review;
    }

    @Transactional
    public Review createAccommodationReview(CreateAccommodationVoteReviewReq dto, String placeId, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        Place place = placeService.findById(placeId);

        // 숙소 리뷰 생성
        Review review = reviewRepository.save(dto.toEntity(dto, user, place));
        // 토표 리뷰 여부 생성
        reviewVoteAccommodationService.createAccommodationReview(review, dto);
        return review;
    }

    /**
     * 음식점 리뷰 수정
     */
    @Transactional
    public Review updateRestaurantReview(UpdateRestaurantVoteReviewReq dto, String placeId, UUID reviewId,
                                         AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        placeService.findById(placeId);

        Review review = findByIdNotDeletedReview(reviewId);
        checkAuthor(review, user);

        reviewVoteRestaurantService.updateRestaurantReview(review, dto);
        // 리뷰 수정
        review.update(dto);

        return review;
    }


    /**
     * 관광지 리뷰 수정
     */
    @Transactional
    public Review updateTouristSpotReview(UpdateTouristSpotVoteReviewReq dto, String placeId, UUID reviewId,
                                          AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        placeService.findById(placeId);

        Review review = findByIdNotDeletedReview(reviewId);
        checkAuthor(review, user);

        reviewVoteTouristSpotService.updateTouristSpotReview(review, dto);
        // 리뷰 수정
        review.update(dto);

        return review;
    }

    /**
     * 리뷰 조회
     */
    public GetReviewRes getReview(UUID reviewId, AuthenticatedUserReq auth) {
        Review review = findByIdNotDeletedReview(reviewId);
        PlaceType placeType = review.getPlace().getTypes();

        switch (placeType) {
            case RESTAURANT -> {
                return GetRestaurantRes.of(review);
            }
//            case LODGING -> {
//
//            }
//            case TOURISTSPOT -> {
//
//            }
        }

        return null; // exception 설정
    }
    /**
     * 한 장소에 대한 리뷰 전체 조회
     */
    public Page<GetReviewRes> getReviews(String placeId, AuthenticatedUserReq auth, String sort, int page) {
        Place place = placeService.findById(placeId);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Review> reviews = reviewRepository.findByReviewOfPlace(place, sort, pageable);

        switch (place.getTypes()) {
            case RESTAURANT -> {
                return GetRestaurantRes.getReviewsPaging(reviews).map(review -> review);
            }
//            case LODGING -> {
//
//            }
//            case TOURISTSPOT -> {
//
//            }
        }
        return null; // exception 설정
    }

    /**
     * 리뷰 삭제
     */
    @Transactional
    public void deleteReview(UUID reviewId, AuthenticatedUserReq auth) {
        Review review = findByIdNotDeletedReview(reviewId);
        User user = userService.findByEmail(auth.getEmail());
        checkAuthor(review, user);
        review.softDelete();
    }

    /**
     * 게시글 작성자 확인
     */
    public void checkAuthor(Review review, User user) {
        if (!review.getUser().getId().equals(user.getId())) {
            throw new NotReviewAuthorException(NOT_POST_AUTHOR_EXCEPTION);
        }
    }

    /**
     * 게시글이 삭제됐다면 exception 발생
     */
    public Review findByIdNotDeletedReview(UUID reviewId) {
        Review review = findById(reviewId);

        if (review.getIsDeleted()) {
            throw new NotFoundReviewException(NOT_FOUND_REVIEW_EXCEPTION);
        }

        return review;
    }

    // TODO 리뷰 수정

    public Review findById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST_EXCEPTION));
    }
}
