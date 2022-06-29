package com.its.project.dto;

import com.its.project.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
  private Long id;
  private Long boardId;
  private String commentWriter;
  private String commentContents;
  private LocalDateTime commentCreatedTime;

  public static CommentDTO toSaveDTO(CommentEntity commentEntity) {
    CommentDTO commentDTO = new CommentDTO();

    commentDTO.setId(commentEntity.getId());
    commentDTO.setCommentWriter(commentEntity.getCommentWriter());
    commentDTO.setCommentContents(commentEntity.getCommentContents());
    commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());

    return commentDTO;
  }
}
