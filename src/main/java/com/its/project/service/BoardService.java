package com.its.project.service;

import com.its.project.dto.BoardDTO;
import com.its.project.entity.BoardEntity;
import com.its.project.entity.MemberEntity;
import com.its.project.repository.BoardRepository;
import com.its.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  public List<BoardDTO> findAll() {
    System.out.println("BoardService.findAll");

    List<BoardEntity> boardEntityList = boardRepository.findAll();
    List<BoardDTO> boardDTOList = new ArrayList<>();

    for (BoardEntity boardEntity : boardEntityList) {
      boardDTOList.add(BoardDTO.toDTO(boardEntity));
    }

    return boardDTOList;
  }

  public Long save(BoardDTO boardDTO) throws IOException {
    System.out.println("BoardService.save");

    MultipartFile boardFile = boardDTO.getBoardFile();
    String boardFileName = boardFile.getOriginalFilename();

    if (!boardFile.isEmpty()) {
      boardFileName = System.currentTimeMillis() + "_" + boardFileName;
      String savePath = "D:\\springboot_img\\" + boardFileName;
      boardFile.transferTo(new File(savePath));
      boardDTO.setBoardFileName(boardFileName);
    }

    Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(boardDTO.getBoardWriter());
    if (optionalMemberEntity.isPresent()) {
      MemberEntity memberEntity = optionalMemberEntity.get();
      BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
      return boardRepository.save(boardEntity).getId();
    } else {
      return null;
    }
  }

  public BoardDTO findById(Long id) {
    System.out.println("BoardService.findById");

    Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
    if (optionalBoardEntity.isPresent()) {
      BoardEntity boardEntity = optionalBoardEntity.get();
      return BoardDTO.toDTO(boardEntity);
    } else {
      return null;
    }
  }
}
