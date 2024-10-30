package com.picnee.travel.domain.post.service;

import com.picnee.travel.domain.board.entity.Board;
import com.picnee.travel.domain.board.service.BoardService;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.dto.req.ModifyPostReq;
import com.picnee.travel.domain.post.dto.res.FindPostRes;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.exception.NotFoundPostException;
import com.picnee.travel.domain.post.exception.NotPostAuthorException;
import com.picnee.travel.domain.post.repository.PostRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.exception.ErrorCode;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
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
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final BoardService boardService;

    /**
     * 게시글 생성
     */
    @Transactional
    public Post create(CreatePostReq dto, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        Board board = boardService.create(dto);

        return postRepository.save(CreatePostReq.toEntity(dto, board, user));
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Post update(UUID postId, ModifyPostReq dto, AuthenticatedUserReq auth) {
        Post post = findByIdNotDeletedPost(postId);
        User user = userService.findByEmail(auth.getEmail());

        checkAuthor(post, user);
        boardService.update(post, dto);
        post.update(dto);
        return post;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(UUID postId, AuthenticatedUserReq auth) {
        Post post = findByIdNotDeletedPost(postId);
        User user = userService.findByEmail(auth.getEmail());

        checkAuthor(post, user);

        post.softDelete();
        boardService.delete(post);
    }

    /**
     * 게시글 단건 조회
     */
    public FindPostRes find(UUID postId, AuthenticatedUserReq auth) {
        Post post = findByIdNotDeletedPost(postId);

        return FindPostRes.from(post);
    }

    public void checkAuthor(Post post, User user) {
        if (!post.getUser().getId().equals(user.getId())) {
            throw new NotPostAuthorException(NOT_POST_AUTHOR_EXCEPTION);
        }
    }

    /**
     * 게시글이 삭제됐다면 exception 발생
     */
    public Post findByIdNotDeletedPost(UUID postId) {
        Post post = findById(postId);

        if (post.getIsDeleted()) {
            throw new NotFoundPostException(NOT_FOUND_POST_EXCEPTION);
        }

        return post;
    }

    /**
     * 게시글 찾기
     */
    public Post findById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST_EXCEPTION));
    }
}
