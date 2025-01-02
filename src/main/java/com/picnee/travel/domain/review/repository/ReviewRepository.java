package com.picnee.travel.domain.review.repository;

import com.picnee.travel.domain.review.dto.res.GetAccommodationQueryRes;
import com.picnee.travel.domain.review.dto.res.GetRestaurantQueryRes;
import com.picnee.travel.domain.review.dto.res.GetTouristSptQueryRes;
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
             , rvr.is_korean_menu
             , rvr.is_smoking
             , rvr.is_kiosk
             , rvr.is_card
             , rvr.is_cash
             , rvr.is_only_reservation
             , rvr.is_reservation
             , rvr.is_tasty
             , rvr.is_local_flavor
             , rvr.is_cultural
             , rvr.is_atmosphere_good
             , rvr.is_solo_friendly
             , rvr.is_group_friendly
             , rvr.is_service_friendly
             , rvr.has_waiting
             , rvr.is_clean
             , rvr.is_cost_effective
          FROM picnee.review re
          LEFT JOIN popular_review pr ON re.review_id = pr.review_id
          LEFT JOIN picnee.review_vote_restaurant rvr ON re.review_id = rvr.review_id
          LEFT JOIN picnee.users u ON re.user_id = u.user_id
         WHERE re.place_id = :placeId
         ORDER BY pr.weighted_score DESC
         LIMIT 3
        """,
            nativeQuery = true
    )
    List<GetRestaurantQueryRes> findByPopularRestaurantReviewFromPlace(@Param("placeId") String placeId);

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
             , rvt.is_paid_entry
             , rvt.is_reservation_required
             , rvt.is_korean_guide_available
             , rvt.is_bike_parking_available
             , rvt.is_car_parking_available
             , rvt.has_historical_tradition
             , rvt.has_many_sights
             , rvt.has_beautiful_night_view
             , rvt.is_photo_friendly
             , rvt.has_good_guidance
             , rvt.has_convenient_facilities
             , rvt.has_experience_programs
             , rvt.has_clean_restrooms
             , rvt.is_easy_public_transport
             , rvt.is_quiet_and_peaceful
          FROM picnee.review re
          LEFT JOIN popular_review pr ON re.review_id = pr.review_id
          LEFT JOIN picnee.review_vote_touristspot rvt ON re.review_id = rvt.review_id
          LEFT JOIN picnee.users u ON re.user_id = u.user_id
         WHERE re.place_id = :placeId
         ORDER BY pr.weighted_score DESC
         LIMIT 3
        """,
            nativeQuery = true
    )
    List<GetTouristSptQueryRes> findByPopularTouristSpotReviewFromPlace(@Param("placeId") String placeId);
}
