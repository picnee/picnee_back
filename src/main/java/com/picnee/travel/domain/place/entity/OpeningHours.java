package com.picnee.travel.domain.place.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import com.picnee.travel.domain.post.entity.Post;
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
@Table(name = "opening_hours")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class OpeningHours {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "opening_hours_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "day")
    private String day;
    @Column(name = "time")
    private String time;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
}