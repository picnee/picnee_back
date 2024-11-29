package com.picnee.travel.api.in;

import com.picnee.travel.domain.place.dto.req.CreatePlaceReq;
import com.picnee.travel.domain.place.dto.res.FindPlaceRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "place", description = "place API")
public interface PlaceApi {
    @Operation(summary = "구글 맵스 Place 저장", description = "구글 맵스에서 정보를 받아와 저장한다.")
    public ResponseEntity<Void> createPlace(CreatePlaceReq dto);

    @Operation(summary = "서버의 Place 정보 받아오기", description = "서버가 가지고있는지 확인 후 가지고 있다면 Place를 반환")
    public ResponseEntity<FindPlaceRes> getPlace(String placeId);
}