package com.picnee.travel.domain.place.repository;

import com.picnee.travel.domain.place.dto.res.FilterPlaceRes;

import java.util.List;
import java.util.Map;

public interface PlaceRepositoryCustom {

    List<FilterPlaceRes> filterPlaces(String region, String type, String sort, Map<String, Boolean> filters);
}
