package com.picnee.travel.domain.review.repository;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

    Page<Review> findByReviewOfPlace(Place place, Pageable pageable);
}
