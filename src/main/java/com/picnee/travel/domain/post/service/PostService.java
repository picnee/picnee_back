package com.picnee.travel.domain.post.service;

import com.picnee.travel.domain.board.entity.Board;
import com.picnee.travel.domain.board.service.BoardService;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.post.repository.PostRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final BoardService boardService;

    @Transactional
    public Post create(CreatePostReq dto, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        Board board = boardService.create(dto);

        return postRepository.save(CreatePostReq.toEntity(dto, board, user));
    }
}
