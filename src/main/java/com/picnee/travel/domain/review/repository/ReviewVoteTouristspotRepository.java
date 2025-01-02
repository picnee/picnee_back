package com.picnee.travel.domain.review.repository;

import com.picnee.travel.domain.review.entity.ReviewVoteTouristspot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewVoteTouristspotRepository extends JpaRepository<ReviewVoteTouristspot, UUID> {
}
