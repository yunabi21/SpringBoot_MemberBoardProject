package com.its.project.service;

import com.its.project.dto.CommentDTO;
import com.its.project.entity.BoardEntity;
import com.its.project.entity.CommentEntity;
import com.its.project.entity.MemberEntity;
import com.its.project.repository.BoardRepository;
import com.its.project.repository.CommentRepository;
import com.its.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;
  public List<CommentDTO> findAll() {
    System.out.println("CommentService.findAll");

    List<CommentEntity> commentEntityList = commentRepository.findAll();
    List<CommentDTO> commentDTOList = new ArrayList<>();

    for (CommentEntity commentEntity : commentEntityList) {
      commentDTOList.add(CommentDTO.toSaveDTO(commentEntity));
    }

    return commentDTOList;
  }

  public void save(CommentDTO commentDTO) {
    System.out.println("CommentService.save");

    Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(commentDTO.getCommentWriter());
    Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());

    if (optionalBoardEntity.isPresent() && optionalMemberEntity.isPresent()) {
      MemberEntity memberEntity = optionalMemberEntity.get();
      BoardEntity boardEntity = optionalBoardEntity.get();
      CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, memberEntity);
      commentRepository.save(commentEntity);
    }
  }

  public CommentDTO findById(Long id) {
    System.out.println("CommentService.findById");

    Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
    if (optionalCommentEntity.isPresent()) {
      CommentEntity commentEntity = optionalCommentEntity.get();
      return CommentDTO.toSaveDTO(commentEntity);
    } else {
      return null;
    }
  }

  public void delete(Long id) {
    System.out.println("CommentService.delete");

    commentRepository.deleteById(id);
  }
}
