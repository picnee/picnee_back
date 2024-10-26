package com.picnee.travel.domain.likePlace.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import com.picnee.travel.domain.likeList.entity.LikeList;
import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Getter
@Entity
@Table(name = "like_place")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class LikePlace extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "like_place_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "like_list_id")
    private LikeList likeList;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
}
