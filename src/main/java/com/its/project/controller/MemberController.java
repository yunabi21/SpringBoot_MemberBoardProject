package com.its.project.controller;

import com.its.project.dto.MemberDTO;
import com.its.project.entity.MemberEntity;
import com.its.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    return "index";
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


}
