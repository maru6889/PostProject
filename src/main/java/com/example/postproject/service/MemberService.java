package com.example.postproject.service;

import com.example.postproject.domain.dto.MemberDto;
import com.example.postproject.exception.MemberNotDeleteException;
import com.example.postproject.exception.MemberNotFoundException;
import com.example.postproject.exception.MemberNotInsertException;
import com.example.postproject.exception.MemberNotUpdateException;
import com.example.postproject.domain.Member;
import com.example.postproject.domain.dto.MemberInsertDto;
import com.example.postproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public List<Member> findMembers(){
        List<Member> members = memberRepository.findMembers();
        return members;
    }

    public Member findMemberByLoginId(String loginId) {
        Member member = memberRepository.findMemberByLoginId(loginId);
//        if (member == null) {
//            throw new MemberNotFoundException("해당 회원을 찾을 수 없습니다.");
//        }
        return member;
    }

    public Member findMemberByNickname(String nickname) {
        return memberRepository.findMemberByNickname(nickname);
    }

    @Transactional
    public int insertMember(MemberInsertDto dto){
        Member member = dto.toEntity(encoder.encode(dto.getPassword()));
        int result = memberRepository.insertMember(member);
//        if (result == 0) {
//            throw new MemberNotInsertException("회원 가입이 이루어지지 않았습니다.");
//        }
        return result;
    }

    @Transactional
    public int updateMember(MemberDto dto, String loginId){
        Member member = memberRepository.findMemberByLoginId(loginId);
        if (!dto.getNickname().isBlank() || !dto.getNickname().isEmpty()) {
            member.setNickname(dto.getNickname());
        }
        if (!dto.getNewPassword().isEmpty() || !dto.getNewPassword().isBlank()) {
            member.setPassword(encoder.encode(dto.getNewPassword()));
        }
        int result = memberRepository.updateMember(loginId, member);
//        if (result == 0) {
//            throw new MemberNotUpdateException("회원 수정이 이루어지지 않았습니다.");
//        }

        return result;
    }

    @Transactional
    public int deleteMember(String loginId) {
        return memberRepository.deleteMember(loginId);
    }
}
