package com.picnee.travel.domain.place.service;

import com.picnee.travel.domain.place.dto.req.CreatePlaceReq;
import com.picnee.travel.domain.place.dto.res.FindPlaceRes;
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

    @Transactional
    public void create(CreatePlaceReq dto) {
        placeRepository.save(CreatePlaceReq.toEntity(dto));
    }

    public FindPlaceRes getPlace(String placeId){
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("여기에 404보내"));

        return FindPlaceRes.from(place);
    }

}
