package com.picnee.travel.domain.board.service;

import com.picnee.travel.domain.board.entity.Board;
import com.picnee.travel.domain.board.exception.NotFoundBoardException;
import com.picnee.travel.domain.board.repository.BoardRepository;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.dto.req.ModifyPostReq;
import com.picnee.travel.domain.post.entity.Post;
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
public class BoardService {

    private final BoardRepository boardRepository;

    public Board create(CreatePostReq dto) {
        Board board = Board.builder()
                .region(dto.getRegion())
                .boardCategory(dto.getBoardCategory())
                .build();

        return boardRepository.save(board);
    }

    public void update(Post post, ModifyPostReq dto) {
        Board board = findByBoardId(post.getBoard().getId());

        board.update(dto);
    }

    public void delete(Post post) {
        Board board = findByBoardId(post.getBoard().getId());

        board.softDelete();
    }

    public Board findByBoardId(UUID boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException(NOT_FOUND_BOARD_EXCEPTION));
    }
}
