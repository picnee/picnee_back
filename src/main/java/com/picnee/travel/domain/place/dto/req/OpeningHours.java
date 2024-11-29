package com.picnee.travel.domain.place.dto.req;

import lombok.*;
import java.util.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OpeningHours {
    private Boolean openNow;
    private List<Period> periods; // `periods` 데이터 표현
    private List<String> weekdayText; // `weekday_text` 데이터 표현
}
