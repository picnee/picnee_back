package com.picnee.travel.domain.postComment.entity;

import com.picnee.travel.domain.base.entity.SoftDeleteBaseEntity;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.postComment.dto.req.UpdatePostCommentReq;
import com.picnee.travel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLRestriction;
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
@Table(name = "post_comment")
@SuperBuilder
@DynamicUpdate
@AllArgsConstructor
@SQLRestriction("is_deleted = false")
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PostComment extends SoftDeleteBaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @UuidGenerator(style = RANDOM)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "post_comment_id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(name = "content")
    private String content;
    @Column(name = "likes")
    private Long likes;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_comment_parent_id")
    private PostComment commentParent;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Builder.Default
    @OneToMany(mappedBy = "commentParent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> children = new ArrayList<>();

    /**
     * 댓글 수정
     */
    public void update(UpdatePostCommentReq dto) {
        this.content = dto.getContent() == null ? this.content : dto.getContent();
    }

    /**
     * 좋아요 추가
     */
    public void addLike() {
        this.likes++;
    }

    /**
     * 좋아요 취소
     */
    public void deleteLike() {
        this.likes--;
    }
}
