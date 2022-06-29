package com.its.project.entity;

import com.its.project.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String commentWriter;

  @Column
  private String commentContents;

  // 댓글(N) - 회원(1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private MemberEntity memberEntity;

  // 댓글(N) - 게시글(1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private BoardEntity boardEntity;

  public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
    CommentEntity commentEntity = new CommentEntity();

    commentEntity.setCommentWriter(memberEntity.getMemberId());
    commentEntity.setCommentContents(commentDTO.getCommentContents());
    commentEntity.setBoardEntity(boardEntity);
    commentEntity.setMemberEntity(memberEntity);

    return commentEntity;
  }
}
