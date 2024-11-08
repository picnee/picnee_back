package com.picnee.travel.domain.postComment.service;

import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.exception.NotFoundPostException;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.postComment.dto.req.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.req.UpdatePostCommentReq;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.exception.NotFoundCommentException;
import com.picnee.travel.domain.postComment.exception.NotValidOwnerException;
import com.picnee.travel.domain.postComment.repository.PostCommentRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommentService {

    private final UserService userService;
    private final PostService postService;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public PostComment create(UUID postId, CreatePostCommentReq dto, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        Post post = postService.findByIdNotDeletedPost(postId);

        return postCommentRepository.save(CreatePostCommentReq.toEntity(dto, user, post));
    }

    @Transactional
    public PostComment update(UUID postId, UUID commentId, UpdatePostCommentReq dto, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        PostComment postComment = findByIdNotDeletedPostComment(commentId);
        // 게시글이 삭제되었는지 확인
        postService.findByIdNotDeletedPost(postId);

        validOwner(postComment, user);

        postComment.update(dto);

        return postComment;
    }

    @Transactional
    public void delete(UUID postId, UUID commentId, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        PostComment postComment = findByIdNotDeletedPostComment(commentId);
        // 게시글이 삭제되었는지 확인
        postService.findByIdNotDeletedPost(postId);

        validOwner(postComment, user);

        postComment.softDelete();
    }

    /**
     * 댓글 주인인지 확인
     */
    private void validOwner(PostComment postComment, User user) {
        if (!postComment.getUser().getId().equals(user.getId())) {
            throw new NotValidOwnerException(NOT_VALID_OWNER_EXCEPTION);
        }
    }

    /**
     * 게시글이 삭제됐다면 exception 발생
     */
    public PostComment findByIdNotDeletedPostComment(UUID commentId) {
        PostComment postComment = findById(commentId);

        if (postComment.getIsDeleted()) {
            throw new NotFoundCommentException(NOT_FOUND_COMMENT_EXCEPTION);
        }

        return postComment;
    }

    public PostComment findById(UUID commentId) {
        return postCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException(NOT_FOUND_COMMENT_EXCEPTION));
    }
}
