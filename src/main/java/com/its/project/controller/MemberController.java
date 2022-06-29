package com.its.project.controller;

import com.its.project.dto.MemberDTO;
import com.its.project.entity.MemberEntity;
import com.its.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/save-form")
  public String saveForm () {
    System.out.println("MemberController.saveForm");

    return "memberPages/save";
  }

  @PostMapping("/save")
  public String save (@ModelAttribute MemberDTO memberDTO) throws IOException {
    System.out.println("MemberController.save");

    memberService.save(memberDTO);

    return "redirect:/";
  }

  @PostMapping("/duplicateCheck")
  public @ResponseBody String duplicateCheck(@RequestParam("memberId") String memberId) {
    System.out.println("MemberController.duplicateCheck");

    MemberEntity memberEntity = memberService.duplicateCheck(memberId);
    if (memberEntity != null) {
      return "no";
    } else {
      return "ok";
    }
  }

  @GetMapping("/login-form")
  public String loginForm () {
    System.out.println("MemberController.loginForm");

    return "memberPages/login";
  }

  @PostMapping("/login")
  public String login (@ModelAttribute MemberDTO memberDTO,
                       HttpSession session) {
    System.out.println("MemberController.login");

    MemberDTO loginDTO = memberService.login(memberDTO);
    if (loginDTO != null) {
      session.setAttribute("id", loginDTO.getId());
      session.setAttribute("loginId", loginDTO.getMemberId());
      return "redirect:/board/";
    } else {
      return "memberPages/login";
    }
  }

  @GetMapping("/{id}")
  public String myPage(@PathVariable("id") Long id, Model model) {
    System.out.println("MemberController.myPage");

    MemberDTO memberDTO = memberService.findById(id);
    model.addAttribute("member", memberDTO);

    return "memberPages/myPage";
  }

  @GetMapping("/findById")
  public @ResponseBody MemberDTO findById(@RequestParam("id") Long id) {
    System.out.println("MemberController.findById");

    return memberService.findById(id);
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Long id, HttpSession session) {
    System.out.println("MemberController.delete");

    if (!Objects.equals(memberService.findById(id).getMemberId(), "admin")) {
      memberService.delete(id);
      session.invalidate();
    }
    return "index";
  }

  @GetMapping("/update/{id}")
  public String updateForm(@PathVariable("id") Long id, Model model) {
    System.out.println("MemberController.updateForm");

    MemberDTO memberDTO = memberService.findById(id);
    model.addAttribute("member", memberDTO);

    return "memberPages/update";
  }

  @PostMapping("/update/{id}")
  public String update(@PathVariable("id") Long id, @ModelAttribute MemberDTO memberDTO) throws IOException {
    System.out.println("MemberController.update");

    memberService.update(memberDTO);

    return "redirect:/member/" + id;
  }
}
