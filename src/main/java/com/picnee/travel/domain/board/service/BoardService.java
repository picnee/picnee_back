package com.picnee.travel.domain.board.service;

import com.picnee.travel.domain.board.entity.Board;
import com.picnee.travel.domain.board.repository.BoardRepository;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board create(CreatePostReq dto) {
        Board board = Board.builder()
                .region(dto.getRegion())
                .boardCategory(dto.getBoardCategory())
                .build();

        return boardRepository.save(board);
    }
}
