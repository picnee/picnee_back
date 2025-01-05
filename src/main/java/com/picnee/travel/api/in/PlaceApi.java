package com.picnee.travel.api.in;

import com.picnee.travel.domain.place.dto.req.CreatePlaceReq;
import com.picnee.travel.domain.place.dto.res.FilterPlaceRes;
import com.picnee.travel.domain.place.dto.res.FindPlaceRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "place", description = "place API")
public interface PlaceApi {
    @Operation(summary = "구글 맵스 Place 저장", description = "구글 맵스에서 정보를 받아와 저장한다.")
    public ResponseEntity<String> createPlace(CreatePlaceReq dto);

    @Operation(summary = "서버의 Place 정보 받아오기", description = "서버가 가지고있는지 확인 후 가지고 있다면 Place를 반환")
    public ResponseEntity<FindPlaceRes> getPlace(String placeId);

    @Operation(summary = "Place 목록 조회", description = "조건(지역, 정렬 방식, 필터링)에 부합하는 Place 목록을 조회한다.")
    public ResponseEntity<List<FilterPlaceRes>> getPlaces(String region, String type, String sort);
}