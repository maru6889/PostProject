package com.example.postproject.service;

import com.example.postproject.exception.MemberNotDeleteException;
import com.example.postproject.exception.MemberNotFoundException;
import com.example.postproject.exception.MemberNotInsertException;
import com.example.postproject.exception.MemberNotUpdateException;
import com.example.postproject.domain.Member;
import com.example.postproject.domain.dto.MemberInsertDto;
import com.example.postproject.domain.dto.MemberUpdateDto;
import com.example.postproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findMembers(){
        List<Member> members = memberRepository.findMembers();
        return members;
    }

    public Member findMemberByLoginId(String loginId) {
        Member member = memberRepository.findMemberByLoginId(loginId);
        if (member == null) {
            throw new MemberNotFoundException("해당 회원을 찾을 수 없습니다.");
        }
        return member;
    }

    public int insertMember(MemberInsertDto dto){
        Member member = dto.toEntity();
        int result = memberRepository.insertMember(member);
        if (result == 0) {
            throw new MemberNotInsertException("회원 가입이 이루어지지 않았습니다.");
        }
        return result;
    }

    public int updateMember(MemberUpdateDto dto, String loginId){
        Member member = memberRepository.findMemberByLoginId(loginId);
        member.setPassword(dto.getNewPassword());
        member.setNickname(dto.getNickname());

        int result = memberRepository.updateMember(loginId, member);
        if (result == 0) {
            throw new MemberNotUpdateException("회원 수정이 이루어지지 않았습니다.");
        }

        return result;
    }

    public int deleteMember(String loginId, String password) {
        Member member = memberRepository.findMemberByLoginId(loginId);

        int result = 0;

        if (member.getPassword().equals(password)) {
            result = memberRepository.deleteMember(loginId);
        }

        if (result == 0) {
            throw new MemberNotDeleteException("회원 탈퇴가 이루어지지 않았습니다.");
        }

        return result;
    }
}
