package com.picnee.travel.domain.review.repository;

import com.picnee.travel.domain.review.entity.ReviewVoteAccommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewVoteAccommodationRepository extends JpaRepository<ReviewVoteAccommodation, UUID> {
}
