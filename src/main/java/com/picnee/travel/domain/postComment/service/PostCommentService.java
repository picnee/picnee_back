package com.picnee.travel.domain.postComment.service;

import com.picnee.travel.domain.notification.dto.event.PostCommentEvent;
import com.picnee.travel.domain.post.dto.res.FindPostRes;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.exception.NotAuthException;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.postComment.dto.req.CreatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.req.UpdatePostCommentReq;
import com.picnee.travel.domain.postComment.dto.res.GetMyPostCommentRes;
import com.picnee.travel.domain.postComment.dto.res.GetPostCommentRes;
import com.picnee.travel.domain.postComment.entity.PostComment;
import com.picnee.travel.domain.postComment.exception.NotFoundCommentException;
import com.picnee.travel.domain.postComment.exception.NotProvideCommentLikeException;
import com.picnee.travel.domain.postComment.exception.NotValidOwnerException;
import com.picnee.travel.domain.postComment.repository.PostCommentRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.domain.userPostCommet.service.UserPostCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommentService {

    private final UserService userService;
    private final PostService postService;
    private final UserPostCommentService userPostCommentService;
    private final PostCommentRepository postCommentRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 댓글 생성
     */
    @Transactional
    public PostComment create(UUID postId, CreatePostCommentReq dto, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        Post post = postService.findByIdNotDeletedPost(postId);

        eventPublisher.publishEvent(new PostCommentEvent(postId));

        return postCommentRepository.save(CreatePostCommentReq.toEntity(dto, user, post));
    }

    /**
     * 댓글 수정
     */
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

    /**
     * 댓글 삭제
     */
    @Transactional
    public void delete(UUID postId, UUID commentId, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        PostComment postComment = findByIdNotDeletedPostComment(commentId);
        // 게시글이 삭제되었는지 확인
        postService.findByIdNotDeletedPost(postId);

        validOwner(postComment, user);

        // 부모 댓글과 자식댓글 삭제
        List<PostComment> childComments = postCommentRepository.findChildrenByParentId(postComment.getId());
        postComment.softDelete();
        childComments.forEach(PostComment::softDelete);
    }

    /**
     * 게시글 관련 댓글 전체 조회
     */
    public List<GetPostCommentRes> getComments(UUID postId, AuthenticatedUserReq auth) {
        Post post = postService.findByIdNotDeletedPost(postId);
        List<PostComment> comments = postCommentRepository.findByCommentsOfPost(post);
        return GetPostCommentRes.from(comments);
    }

    /**
     * 대댓글 생성
     */
    @Transactional
    public PostComment createChildrenComment(UUID postId, UUID commentId, CreatePostCommentReq dto, AuthenticatedUserReq auth) {
        Post post = postService.findByIdNotDeletedPost(postId);
        PostComment postComment = findByIdNotDeletedPostComment(commentId);
        User user = userService.findByEmail(auth.getEmail());

        return postCommentRepository.save(CreatePostCommentReq.toEntityCoComment(post, postComment, user, dto));
    }

    @Transactional
    public void toggleLike(UUID postId, UUID commentId, AuthenticatedUserReq auth) {
        // 로그인한 사용자만 가능
        if (isUserAuthenticated(auth)) {
            throw new NotProvideCommentLikeException(NOT_PROVIDE_COMMENT_LIKE_EXCEPTION);
        }

        postService.findByIdNotDeletedPost(postId);
        PostComment postComment = findByIdNotDeletedPostComment(commentId);
        User user = userService.findByEmail(auth.getEmail());

        boolean likeState = userPostCommentService.incrementLike(postComment, user);

        if (!likeState) {
            postComment.addLike();
            return;
        }

        postComment.deleteLike();
    }

    /**
     * 내가 작성한 댓글 확인
     */
    public Page<GetMyPostCommentRes> getMyComments(int page, AuthenticatedUserReq auth) {
        if(isUserAuthenticated(auth)) {
            throw new NotAuthException(NOT_AUTH_EXCEPTION);
        }

        User user = userService.findByEmail(auth.getEmail());
        Pageable pageable = PageRequest.of(page, 10);

        Page<PostComment> myComments = postCommentRepository.getMyComments(pageable, user.getId());
        return GetMyPostCommentRes.paging(myComments);
    }


    /**
     * 로그인한 유저인지 확인
     */
    private boolean isUserAuthenticated(AuthenticatedUserReq auth) {
        return auth == null;
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
