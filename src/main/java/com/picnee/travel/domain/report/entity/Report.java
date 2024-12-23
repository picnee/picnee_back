package com.picnee.travel.domain.report.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
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
@Table(name = "report")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Report extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "report_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "target_id", columnDefinition = "VARCHAR(36)")
    private UUID targetId;
    @Column(name = "report_target_type")
    @Enumerated(EnumType.STRING)
    private ReportTargetType reportTargetType;
    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    @Column(name = "is_visible")
    private Boolean isVisible;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
