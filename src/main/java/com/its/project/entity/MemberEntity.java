package com.its.project.entity;

import com.its.project.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "member_table")
public class MemberEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String memberId;

  @Column(nullable = false)
  private String memberPassword;

  @Column(nullable = false)
  private String memberName;

  @Column
  private String memberEmail;

  @Column
  private String memberMobile;

  @Column
  private String memberProfileName;


  public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setMemberId(memberDTO.getMemberId());
    memberEntity.setMemberPassword(memberDTO.getMemberPassword());
    memberEntity.setMemberName(memberDTO.getMemberName());
    memberEntity.setMemberEmail(memberDTO.getMemberEmail());
    memberEntity.setMemberMobile(memberDTO.getMemberMobile());
    memberEntity.setMemberProfileName(memberDTO.getMemberProfileName());

    return memberEntity;
  }
}
