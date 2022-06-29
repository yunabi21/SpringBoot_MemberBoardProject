package com.its.project.repository;

import com.its.project.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
  List<BoardEntity> findByBoardTitleContaining(String q);

  @Modifying
  @Query(value = "UPDATE board_table b SET b.board_hits = b.board_hits + 1 WHERE b.id = :id", nativeQuery = true)
  void updateBoardHits(@Param("id") Long id);
}
