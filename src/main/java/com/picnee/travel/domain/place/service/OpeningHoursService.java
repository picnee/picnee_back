package com.picnee.travel.domain.place.service;

import com.picnee.travel.domain.place.dto.req.OpeningHoursReq;
import com.picnee.travel.domain.place.entity.OpeningHours;
import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.repository.OpeningHoursRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;

    public void create(List<OpeningHoursReq> dto, Place place) {
        List<OpeningHours> openingHoursEntities = dto.stream()
                .map(req -> OpeningHoursReq.toEntity(req, place))
                .toList();

        openingHoursRepository.saveAll(openingHoursEntities);
    }
}
