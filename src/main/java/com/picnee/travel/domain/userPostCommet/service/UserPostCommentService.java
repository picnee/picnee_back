package com.picnee.travel.domain.userPostCommet.service;

import com.picnee.travel.domain.notification.dto.event.CommentLikeEvent;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.userPostCommet.entity.UserPostComment;
import com.picnee.travel.domain.userPostCommet.repository.UserPostCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPostCommentService {

    private final UserPostCommentRepository userPostCommentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public boolean incrementLike(PostComment postComment, User user) {
        UserPostComment userPostComment = userPostCommentRepository.findByUserAndPostComment(user, postComment)
                .orElseGet(() -> createUserPostComment(user, postComment));

        boolean likeState = userPostComment.isLiked();

        // 좋아요 x -> 좋아요
        if (!likeState) {
            userPostComment.like();
            eventPublisher.publishEvent(new CommentLikeEvent(userPostComment.getPostComment().getId()));
            log.info("@@@@@userPostCommentId = {}", userPostComment.getPostComment().getId());
            return likeState;
        }

        // 좋아요 -> 좋아요 x
        userPostComment.dislike();
        return likeState;
    }


    private UserPostComment createUserPostComment(User user, PostComment postComment) {
        UserPostComment userPostComment = UserPostComment.builder()
                .user(user)
                .postComment(postComment)
                .isLiked(false)
                .build();

        return userPostCommentRepository.save(userPostComment);
    }
}
