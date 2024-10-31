package com.picnee.travel.domain.board.repository;

import com.picnee.travel.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {
}
