package com.picnee.travel.domain.place.service;

import com.picnee.travel.domain.place.dto.req.CreatePlaceReq;
import com.picnee.travel.domain.place.dto.res.FindPlaceRes;
import com.picnee.travel.domain.place.entity.OpeningHours;
import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final OpeningHoursService openingHoursService;

    @Transactional
    public String create(CreatePlaceReq dto) {
        Place place = placeRepository.save(CreatePlaceReq.toEntity(dto));
        openingHoursService.create(dto.getOpeningHoursList(), place);

        return place.getId();
    }

    public FindPlaceRes getPlace(String placeId){
        Place place = findById(placeId);
        return FindPlaceRes.from(place);
    }

    public Place findById(String placeId){
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("여기에 404보내"));
    }
    
}
