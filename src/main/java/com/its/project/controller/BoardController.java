package com.its.project.controller;

import com.its.project.dto.BoardDTO;
import com.its.project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  public final BoardService boardService;

  @GetMapping("/list")
  public String list(Model model) {
    System.out.println("BoardController.list");

    List<BoardDTO> boardDTOList = boardService.findAll();
    model.addAttribute("boardList", boardDTOList);

    return "boardPages/list";
  }

  @GetMapping("/save-form")
  public String saveForm() {
    System.out.println("BoardController.saveForm");

    return "boardPages/save";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
    System.out.println("BoardController.save");

    Long id = boardService.save(boardDTO);

    return "redirect:/board/list";
  }
}
