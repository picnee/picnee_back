package com.picnee.travel.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.picnee.travel.domain.base.entity.SoftDeleteBaseEntity;
import com.picnee.travel.domain.user.dto.req.UpdateUser;
import com.picnee.travel.domain.usersPost.entity.UsersPost;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Getter
@Entity
@Table(name = "users")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class User extends SoftDeleteBaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "user_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "birth_date")
    private String birthDate;
    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "social_root")
    private String socialRoot;
    @JsonProperty("password_count")
    @Column(name = "password_count")
    private Integer passwordCount;
    @Column(name = "account_lock")
    private Boolean accountLock;
    @Column(name = "last_password_expired")
    private LocalDateTime lastPasswordExpired;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "is_marketing")
    private Boolean isMarketing;
    @Column(name = "is_alarm")
    private Boolean isAlarm;
    @Column(name = "is_default_nickname")
    private boolean isDefaultNickname;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UsersPost> usersPosts = new ArrayList<>();

    public void failPasswordCount() {
        this.passwordCount++;
    }

    public void resetPasswordCount() {
        this.passwordCount = 0;
    }

    public void updateLockedStatus() {
        this.state = State.LOCKED;
    }

    public void changeNullState() {
        this.state = null;
    }

    public void updateDefaultNickname(String nickname) {
        this.nickname = nickname;
        this.isDefaultNickname = false;
    }

    /**
     * 내 정보 수정
     */
    public void update(UpdateUser dto, PasswordEncoder passwordEncoder) {
        this.nickname = dto.getNickname() == null ? this.nickname : dto.getNickname();
        this.password = dto.getNewPassword() == null ? this.password : passwordEncoder.encode(dto.getNewPassword());
        this.phoneNumber = dto.getPhoneNumber() == null ? this.phoneNumber : dto.getPhoneNumber().replaceAll("-", "");
        this.birthDate = dto.getBirthDate() == null ? this.birthDate : dto.getBirthDate();
        this.gender = dto.getGender() == null ? this.gender : dto.getGender();
        this.isMarketing = dto.getIsMarketing() == null ? this.isMarketing : dto.getIsMarketing();
        this.isAlarm = dto.getIsAlarm() == null ? this.isAlarm : dto.getIsAlarm();
    }
}
