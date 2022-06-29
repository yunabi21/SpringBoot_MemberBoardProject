package com.its.project.controller;

import com.its.project.dto.CommentDTO;
import com.its.project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
  private final CommentService commentService;

  @GetMapping("/")
  public List<CommentDTO> findAll() {
    System.out.println("CommentController.findAll");

    return commentService.findAll();
  }

  @PostMapping("/save")
  public String save(@ModelAttribute CommentDTO commentDTO) {
    System.out.println("CommentController.save");

    commentService.save(commentDTO);

    return "redirect:/board/" + commentDTO.getBoardId();
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Long id) {
    System.out.println("CommentController.delete");

    commentService.delete(id);

    return "redirect:/board/";
  }
}
