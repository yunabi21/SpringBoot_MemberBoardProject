package com.its.project.controller;

import com.its.project.common.PagingConst;
import com.its.project.dto.BoardDTO;
import com.its.project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

  @GetMapping
  public String list(@PageableDefault(page = 1) Pageable pageable, Model model) {
    System.out.println("BoardController.list");

    Page<BoardDTO> boardList = boardService.paging(pageable);
    model.addAttribute("boardList", boardList);

    int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
    int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();

    model.addAttribute("startPage", startPage);
    model.addAttribute("endPage", endPage);

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

    return "redirect:/board/";
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

    return "redirect:/board/";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Long id) {
    System.out.println("BoardController.delete");

    boardService.delete(id);

    return "redirect:/board/";
  }

  @GetMapping("/search")
  public @ResponseBody List<BoardDTO> search(@RequestParam("q") String q) {
    System.out.println("BoardController.search");

    return boardService.search(q);
  }
}
