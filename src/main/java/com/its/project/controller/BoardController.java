package com.its.project.controller;

import com.its.project.dto.BoardDTO;
import com.its.project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    boardService.save(boardDTO);

    return "redirect:/board/list";
  }

  @GetMapping("/{id}")
  public String detail(@PathVariable("id") Long id, Model model) {
    System.out.println("BoardController.detail");

    BoardDTO boardDTO = boardService.findById(id);
    model.addAttribute("board", boardDTO);

    return "boardPages/detail";
  }

  @GetMapping("/update/{id}")
  public String updateForm(@PathVariable("id") Long id, Model model) {
    System.out.println("BoardController.updateForm");

    BoardDTO boardDTO = boardService.findById(id);
    model.addAttribute("board", boardDTO);

    return "boardPages/update";
  }

  @PostMapping("/update/{id}")
  public String update(@PathVariable("id") Long id, @ModelAttribute BoardDTO boardDTO) throws IOException {
    System.out.println("BoardController.update");

    boardService.update(boardDTO);

    return "redirect:/board/list";
  }
}
