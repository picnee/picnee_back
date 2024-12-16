package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.service.PlaceService;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.exception.NotFoundPostException;
import com.picnee.travel.domain.post.exception.NotPostAuthorException;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.review.dto.req.CreateAccommodationVoteReviewReq;
import com.picnee.travel.domain.review.dto.req.CreateRestaurantVoteReviewReq;
import com.picnee.travel.domain.review.dto.req.CreateReviewReq;
import com.picnee.travel.domain.review.dto.req.CreateTouristspotVoteReviewReq;
import com.picnee.travel.domain.review.dto.res.GetReviewRes;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteAccommodation;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.review.entity.ReviewVoteTouristspot;
import com.picnee.travel.domain.review.exception.NotFoundReviewException;
import com.picnee.travel.domain.review.exception.NotReviewAuthorException;
import com.picnee.travel.domain.review.repository.ReviewRepository;
import com.picnee.travel.domain.review.repository.ReviewVoteAccommodationRepository;
import com.picnee.travel.domain.review.repository.ReviewVoteRestaurantRepository;
import com.picnee.travel.domain.review.repository.ReviewVoteTouristspotRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
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
    private final ReviewVoteAccommodationRepository reviewVoteAccommodationRepository;
    private final ReviewVoteRestaurantRepository reviewVoteRestaurantRepository;
    private final ReviewVoteTouristspotRepository reviewVoteTouristspotRepository;
    private final PlaceService placeService;
    private final UserService userService;

    // TODO 중복 제거 리팩토링
    public Review createAccommodationVoteReview(CreateAccommodationVoteReviewReq dto, AuthenticatedUserReq auth) {
        User                    user        = userService.findByEmail(auth.getEmail());
        Place                   place       = placeService.findById(dto.getPlaceId());
        Review                  review      = reviewRepository.save(dto.toEntity(user, place));
        ReviewVoteAccommodation voteReview  = reviewVoteAccommodationRepository.save(dto.toEntity(review));
        return review;
    }

    public Review createRestaurantVoteReview(CreateRestaurantVoteReviewReq dto, AuthenticatedUserReq auth) {
        User                    user        = userService.findByEmail(auth.getEmail());
        Place                   place       = placeService.findById(dto.getPlaceId());
        Review                  review      = reviewRepository.save(dto.toEntity(user, place));
        ReviewVoteRestaurant    voteReview  = reviewVoteRestaurantRepository.save(dto.toEntity(review));
        return review;
    }

    public Review createRestaurantVoteReview(CreateTouristspotVoteReviewReq dto, AuthenticatedUserReq auth) {
        User                    user        = userService.findByEmail(auth.getEmail());
        Place                   place       = placeService.findById(dto.getPlaceId());
        Review                  review      = reviewRepository.save(dto.toEntity(user, place));
        ReviewVoteTouristspot   voteReview  = reviewVoteTouristspotRepository.save(dto.toEntity(review));
        return review;
    }

    public Review createReview(CreateReviewReq dto, AuthenticatedUserReq auth) {
        User    user        = userService.findByEmail(auth.getEmail());
        Place   place       = placeService.findById(dto.getPlaceId());
        return reviewRepository.save(dto.toEntity(user, place));
    }

    /**
     * 리뷰 조회
     */
    public GetReviewRes getReview(UUID reviewId, AuthenticatedUserReq auth) {
        Review review = findByIdNotDeletedReview(reviewId);
        return GetReviewRes.of(review, auth);
    }

    /**
     * 한 장소에 대한 리뷰 전체 조회
     */
    public Page<GetReviewRes> getReviews(String placeId, AuthenticatedUserReq auth, int page) {
        Place place = placeService.findById(placeId);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Review> review = reviewRepository.findByReviewOfPlace(place, pageable);
        return GetReviewRes.paging(review);
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
