package com.its.project.service;

import com.its.project.common.PagingConst;
import com.its.project.dto.BoardDTO;
import com.its.project.entity.BoardEntity;
import com.its.project.entity.MemberEntity;
import com.its.project.repository.BoardRepository;
import com.its.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

  @Transactional
  public void update(BoardDTO boardDTO) throws IOException {
    System.out.println("BoardService.update");

    BoardDTO findDTO = findById(boardDTO.getId());

    MultipartFile boardFile = boardDTO.getBoardFile();
    String boardFileName = boardFile.getOriginalFilename();

    if (!Objects.equals(findDTO.getBoardFileName(), boardDTO.getBoardFileName())) {
      // 기존 파일이 있을 때
      if (!boardFile.isEmpty()) {
        // 다른 파일로 수정
        boardFileName = System.currentTimeMillis() + "_" + boardFileName;
        String savePath = "D:\\springboot_img\\" + boardFileName;
        boardFile.transferTo(new File(savePath));
        boardDTO.setBoardFileName(boardFileName);
      } else {
        // 기존 파일 삭제
        boardDTO.setBoardFileName(null);
      }
    } else if (findDTO.getBoardFileName() == null) {
      // 기존 파일이 없을 때
      if (!boardFile.isEmpty()) {
        // 파일 업로드
        boardFileName = System.currentTimeMillis() + "_" + boardFileName;
        String savePath = "D:\\springboot_img\\" + boardFileName;
        boardFile.transferTo(new File(savePath));
        boardDTO.setBoardFileName(boardFileName);
      } else {
        // 기존과 똑같이 파일 업로드 x
        boardDTO.setBoardFileName(null);
      }
    }

    Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(boardDTO.getBoardWriter());
    if (optionalMemberEntity.isPresent()) {
      MemberEntity memberEntity = optionalMemberEntity.get();
      BoardEntity boardEntity = BoardEntity.toUpdateBoardEntity(boardDTO, memberEntity);

      boardRepository.save(boardEntity);
    }
  }

  public void delete(Long id) {
    System.out.println("BoardService.delete");

    boardRepository.deleteById(id);
  }

  public Page<BoardDTO> paging(Pageable pageable) {
    System.out.println("BoardService.paging");

    int page = pageable.getPageNumber();
    page = (page == 1)? 0: (page-1);

    Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

    return boardEntities.map(
            board -> new BoardDTO(board.getId(),
                    board.getBoardTitle(),
                    board.getBoardContents(),
                    board.getBoardWriter(),
                    board.getBoardHits(),
                    board.getCreatedTime(),
                    board.getBoardFileName()
            ));
  }
}
