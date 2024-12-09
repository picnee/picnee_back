package com.picnee.travel.api;

import com.picnee.travel.api.in.PlaceApi;
import com.picnee.travel.domain.place.dto.req.CreatePlaceReq;
import com.picnee.travel.domain.place.dto.res.FindPlaceRes;
import com.picnee.travel.domain.place.service.PlaceService;
import com.picnee.travel.domain.user.dto.req.*;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController implements PlaceApi {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<String> createPlace(@Valid @RequestBody CreatePlaceReq dto) {
        String placeId = placeService.create(dto);
        return ResponseEntity.status(CREATED).body(placeId);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<FindPlaceRes> getPlace(@PathVariable("placeId") String placeId) {
        FindPlaceRes res = placeService.getPlace(placeId);

        return ResponseEntity.status(OK).body(res);
    }
}