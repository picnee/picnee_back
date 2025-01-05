package com.picnee.travel.domain.review.repository;

import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewVoteRestaurantRepository extends JpaRepository<ReviewVoteRestaurant, UUID> {
}
