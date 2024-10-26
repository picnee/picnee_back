package com.picnee.travel.domain.comment.entity;

import com.picnee.travel.domain.base.entity.SoftDeleteBaseEntity;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Getter
@Entity
@Table(name = "comment")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Comment extends SoftDeleteBaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "comment_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "content")
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_parent_id")
    private Comment parent;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

}
