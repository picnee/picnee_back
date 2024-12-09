package com.picnee.travel.domain.place.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.entity.PostComment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "place")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Place extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "place_id")
    private String id;
    @Column(name = "place_name")
    private String placeName;
    @Column(name = "url")
    private String url;
    @Column(name = "formatted_address")
    private String formattedAddress;
    @Column(name = "formatted_phone_number")
    private String formattedPhoneNumber;
    @Column(name = "user_ratings_total")
    private Double userRatingsTotal;
    @Column(name = "website")
    private String website;
    @Column(name = "lat")
    private String lat;
    @Column(name = "lng")
    private String lng;
    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OpeningHours> openingHours = new ArrayList<>();
}
