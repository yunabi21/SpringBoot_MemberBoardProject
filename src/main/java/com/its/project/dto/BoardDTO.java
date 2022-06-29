package com.its.project.dto;

import com.its.project.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
  private Long id;
  private String boardWriter;
  private String boardTitle;
  private String boardContents;
  private int boardHits;
  private LocalDateTime boardCreatedDate;
  private MultipartFile boardFile;
  private String boardFileName;

  public BoardDTO(String boardWriter, String boardTitle, String boardContents, int boardHits, LocalDateTime boardCreatedDate) {
    this.boardWriter = boardWriter;
    this.boardTitle = boardTitle;
    this.boardContents = boardContents;
    this.boardHits = boardHits;
    this.boardCreatedDate = boardCreatedDate;
  }

  public BoardDTO(Long id, String boardTitle, String boardContents, String boardWriter, int boardHits, LocalDateTime createdTime, String boardFileName) {
    this.id = id;
    this.boardWriter = boardWriter;
    this.boardTitle = boardTitle;
    this.boardContents = boardContents;
    this.boardHits = boardHits;
    this.boardCreatedDate = createdTime;
    this.boardFileName = boardFileName;
  }

  public static BoardDTO toDTO(BoardEntity boardEntity) {
    BoardDTO boardDTO = new BoardDTO();

    boardDTO.setId(boardEntity.getId());
    boardDTO.setBoardWriter(boardEntity.getBoardWriter());
    boardDTO.setBoardTitle(boardEntity.getBoardTitle());
    boardDTO.setBoardContents(boardEntity.getBoardContents());
    boardDTO.setBoardHits(boardEntity.getBoardHits());
    boardDTO.setBoardCreatedDate(boardEntity.getCreatedTime());
    boardDTO.setBoardFileName(boardEntity.getBoardFileName());

    return boardDTO;
  }
}
