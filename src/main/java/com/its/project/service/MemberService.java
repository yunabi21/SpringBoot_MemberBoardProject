package com.its.project.service;

import com.its.project.dto.MemberDTO;
import com.its.project.entity.MemberEntity;
import com.its.project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  public Long save(MemberDTO memberDTO) throws IOException {
    System.out.println("MemberService.save");

    MultipartFile memberProfile = memberDTO.getMemberProfile();
    String memberProfileName = memberProfile.getOriginalFilename();

    if (!memberProfile.isEmpty()) {
      memberProfileName = System.currentTimeMillis() + "_" + memberProfileName;
      String savePath = "D:\\springboot_img\\" + memberProfileName;
      memberProfile.transferTo(new File(savePath));
      memberDTO.setMemberProfileName(memberProfileName);
    }

    MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);

    return memberRepository.save(memberEntity).getId();
  }

  public MemberEntity duplicateCheck(String memberId) {
    System.out.println("MemberService.duplicateCheck");

    Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberId);
    if (optionalMemberEntity.isEmpty()) {
      return null;
    } else {
      return optionalMemberEntity.get();
    }
  }

  public MemberDTO login(MemberDTO memberDTO) {
    System.out.println("MemberService.login");

    Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberId(memberDTO.getMemberId());
    if (optionalMemberEntity.isPresent()) {
      MemberEntity memberEntity = optionalMemberEntity.get();
      if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
        return MemberDTO.toDTO(memberEntity);
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public MemberDTO findById(Long id) {
    System.out.println("MemberService.findById");

    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
    if (optionalMemberEntity.isPresent()) {
      MemberEntity memberEntity = optionalMemberEntity.get();
      return MemberDTO.toDTO(memberEntity);
    } else {
      return null;
    }
  }

  public void delete(Long id) {
    System.out.println("MemberService.delete");

    memberRepository.deleteById(id);
  }

  public void update(MemberDTO memberDTO) throws IOException {
    System.out.println("MemberService.update");

    MemberDTO findDTO = findById(memberDTO.getId());

    MultipartFile memberProfile = memberDTO.getMemberProfile();
    String memberProfileName = memberProfile.getOriginalFilename();

    if (!Objects.equals(findDTO.getMemberProfileName(), memberDTO.getMemberProfileName())) {
      // 기존 파일이 있을 때
      if (!memberProfile.isEmpty()) {
        memberProfileName = System.currentTimeMillis() + "_" + memberProfileName;
        String savePath = "D:\\springboot_img\\" + memberProfileName;
        memberProfile.transferTo(new File(savePath));
        memberDTO.setMemberProfileName(memberProfileName);
      } else {
        // 기존 파일 삭제
        memberDTO.setMemberProfileName(null);
      }
    } else if (findDTO.getMemberProfileName() == null) {
      // 기존 파일이 없을 때
      if (!memberProfile.isEmpty()) {
        // 파일 업로드
        memberProfileName = System.currentTimeMillis() + "_" + memberProfileName;
        String savePath = "D:\\springboot_img\\" + memberProfileName;
        memberProfile.transferTo(new File(savePath));
        memberDTO.setMemberProfileName(memberProfileName);
      } else {
        // 기존과 똑같이 파일 업로드 x
        memberDTO.setMemberProfileName(null);
      }
    }

    MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
    memberRepository.save(memberEntity);
  }

  public List<MemberDTO> findAll() {
    System.out.println("MemberService.findAll");

    List<MemberEntity> memberEntityList = memberRepository.findAll();
    List<MemberDTO> memberDTOList = new ArrayList<>();

    for (MemberEntity memberEntity : memberEntityList) {
      memberDTOList.add(MemberDTO.toDTO(memberEntity));
    }

    return memberDTOList;
  }
}
