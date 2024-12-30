package com.picnee.travel.domain.review.repository;

import com.picnee.travel.domain.review.dto.res.GetAccommodationQueryRes;
import com.picnee.travel.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewRepositoryCustom {

    @Query(
            value = """
        WITH popular_review AS (
            SELECT r.review_id
                 , SUM(CASE WHEN ur.good_and_bad = TRUE THEN 2 ELSE 0 END) + 
                   SUM(CASE WHEN ur.good_and_bad = FALSE THEN -1 ELSE 0 END) AS weighted_score
              FROM picnee.review r
              LEFT JOIN picnee.users_review ur ON r.review_id = ur.review_id
             GROUP BY r.review_id
        )
        SELECT pr.weighted_score
             , re.review_id
             , re.good_points
             , re.low_points
             , re.place_tips
             , re.rating
             , re.created_at
             , u.user_id as userId
             , rva.has_self_check_in_out
             , rva.has_24hr_print_service
             , rva.provides_breakfast
             , rva.has_luggage_storage
             , rva.has_facilities
             , rva.has_large_bath
             , rva.has_spacious_rooms
             , rva.has_clean_rooms
             , rva.has_good_room_view
             , rva.has_historical_tradition
             , rva.has_comfortable_beds
             , rva.has_good_heating_cooling
             , rva.has_good_soundproofing
             , rva.has_delicious_breakfast
             , rva.has_friendly_service
             , rva.is_easy_public_transport
          FROM picnee.review re
          LEFT JOIN popular_review pr ON re.review_id = pr.review_id
          LEFT JOIN picnee.review_vote_accommodation rva ON re.review_id = rva.review_id
          LEFT JOIN picnee.users u ON re.user_id = u.user_id
         WHERE re.place_id = :placeId
         ORDER BY pr.weighted_score DESC
         LIMIT 3
        """,
            nativeQuery = true
    )
    List<GetAccommodationQueryRes> findByPopularLodgingReviewFromPlace(@Param("placeId") String placeId);


}
