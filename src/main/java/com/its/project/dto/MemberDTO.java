package com.its.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
  private Long id;
  private String memberId;
  private String memberPassword;
  private String memberEmail;
  private String memberName;
  private String memberMobile;
  private MultipartFile memberProfile;
  private String memberProfileName;
  private LocalDateTime memberCreatedTime;
  private LocalDateTime memberUpdatedTime;



}
