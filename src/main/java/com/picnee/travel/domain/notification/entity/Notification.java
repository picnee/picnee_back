package com.picnee.travel.domain.notification.entity;

import com.picnee.travel.domain.base.entity.BaseEntity;
import com.picnee.travel.domain.base.entity.SoftDeleteBaseEntity;
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
@Table(name = "notification")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Notification extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "notification_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "target_id", columnDefinition = "VARCHAR(36)")
    private UUID targetId;
    @Column(name = "is_read")
    private boolean isRead;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateIsRead() {
        this.isRead = true;
    }
}
