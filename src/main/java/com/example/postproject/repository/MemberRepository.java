package com.example.postproject.repository;

import com.example.postproject.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberRepository {

    //조회
    List<Member> findMembers();

    Member findMemberByLoginId(String loginId);

    Member findMemberByNickname(String nickname);

    //생성
    int insertMember(Member member);

    //수정
    int updateMember(@Param("loginId") String loginId, @Param("member") Member member);
    //삭제

    int deleteMember(String loginId);
}
