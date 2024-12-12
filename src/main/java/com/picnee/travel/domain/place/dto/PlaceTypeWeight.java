package com.picnee.travel.domain.place.dto;


import com.picnee.travel.domain.place.entity.PlaceType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * lodging일 경우 반드시 숙소 반환
 * 나머지 가중치 합산을 통해 음수이거나 0일 경우 관광지 양수일 경우 음식점
 */
@Slf4j
public enum PlaceTypeWeight {
    AMUSEMENT_PARK("amusement_park", -5),
    AQUARIUM("aquarium", -5),
    ART_GALLERY("art_gallery", -5),
    MUSEUM("museum", -5),
    PARK("park", -5),
    TOURIST_ATTRACTION("tourist_attraction", -5),
    ZOO("zoo", -5),
    BAKERY("bakery", 5),
    BAR("bar", 5),
    CAFE("cafe", 5),
    RESTAURANT("restaurant", 5);

    private final String name;
    private final int weight;

    PlaceTypeWeight(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    private static Optional<PlaceTypeWeight> fromName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.name.equalsIgnoreCase(name))
                .findFirst();
    }

    public static PlaceType filterType(List<String> types){
        boolean hasLodging = types.stream()
                .anyMatch(type -> type.equalsIgnoreCase("lodging"));
        if (hasLodging) {
            return PlaceType.LODGING;
        }

        return types.stream()
                .map(PlaceTypeWeight::fromName)
                .flatMap(Optional::stream)
                .mapToInt(type -> type.weight)
                .sum() <= 0 ? PlaceType.TOURISTSPOT : PlaceType.RESTAURANT;
    }
}
