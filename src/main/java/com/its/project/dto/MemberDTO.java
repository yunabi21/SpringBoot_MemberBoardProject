package com.its.project.dto;

import com.its.project.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

  public static MemberDTO toDTO (MemberEntity memberEntity) {
    MemberDTO memberDTO = new MemberDTO();

    memberDTO.setId(memberEntity.getId());
    memberDTO.setMemberId(memberEntity.getMemberId());
    memberDTO.setMemberPassword(memberEntity.getMemberPassword());
    memberDTO.setMemberName(memberEntity.getMemberName());
    memberDTO.setMemberEmail(memberEntity.getMemberEmail());
    memberDTO.setMemberMobile(memberEntity.getMemberMobile());
    memberDTO.setMemberProfileName(memberEntity.getMemberProfileName());

    return memberDTO;
  }

}
