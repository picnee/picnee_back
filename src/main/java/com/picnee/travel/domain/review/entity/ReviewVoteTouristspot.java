package com.picnee.travel.domain.review.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Getter
@Entity
@Table(name = "review_vote_touristspot")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ReviewVoteTouristspot {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "review_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "is_accessibility_positive")
    private Boolean isAccessibilityPositive;
    @Column(name = "is_crowded")
    private Boolean isCrowded;
    @Column(name = "is_experience_positive")
    private Boolean isExperiencePositive;
}