package com.its.project.entity;

import com.its.project.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

  // 회원(1) - 게시글(N) 연관관계 (on delete set null)
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  private List<BoardEntity> boardEntityList = new ArrayList<>();

  // 회원(1) - 댓글(N)
  @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
  private List<CommentEntity> commentEntityList = new ArrayList<>();

  @PreRemove
  private void preRemove() {
    boardEntityList.forEach(board -> board.setMemberEntity(null));
    commentEntityList.forEach(comment -> comment.setBoardEntity(null));
  }

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

  public static MemberEntity toUpdateEntity(MemberDTO memberDTO) {
    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setId(memberDTO.getId());
    memberEntity.setMemberId(memberDTO.getMemberId());
    memberEntity.setMemberPassword(memberDTO.getMemberPassword());
    memberEntity.setMemberName(memberDTO.getMemberName());
    memberEntity.setMemberEmail(memberDTO.getMemberEmail());
    memberEntity.setMemberMobile(memberDTO.getMemberMobile());
    memberEntity.setMemberProfileName(memberDTO.getMemberProfileName());

    return memberEntity;
  }
}
