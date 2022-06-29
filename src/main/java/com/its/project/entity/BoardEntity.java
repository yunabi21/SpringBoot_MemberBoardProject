package com.its.project.entity;

import com.its.project.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String boardWriter;

  @Column
  private String boardTitle;

  @Column
  private String boardContents;

  @Column
  @ColumnDefault("0")
  private int boardHits;

  @Column
  private String boardFileName;

  // 게시글(1) - 회원(N) 연관관계
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private MemberEntity memberEntity;

  public static BoardEntity toSaveEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
    BoardEntity boardEntity = new BoardEntity();

    boardEntity.setBoardWriter(memberEntity.getMemberId());
    boardEntity.setBoardTitle(boardDTO.getBoardTitle());
    boardEntity.setBoardContents(boardDTO.getBoardContents());
    boardEntity.setBoardHits(boardDTO.getBoardHits());
    boardEntity.setBoardFileName(boardDTO.getBoardFileName());
    boardEntity.setMemberEntity(memberEntity);

    return boardEntity;
  }

  public static BoardEntity toUpdateBoardEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
    BoardEntity boardEntity = new BoardEntity();

    boardEntity.setId(boardDTO.getId());
    boardEntity.setBoardWriter(memberEntity.getMemberId());
    boardEntity.setBoardTitle(boardDTO.getBoardTitle());
    boardEntity.setBoardContents(boardDTO.getBoardContents());
    boardEntity.setBoardHits(boardDTO.getBoardHits());
    boardEntity.setBoardFileName(boardDTO.getBoardFileName());
    boardEntity.setMemberEntity(memberEntity);

    return boardEntity;
  }
}
