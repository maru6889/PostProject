package com.example.postproject.repository;

import com.example.postproject.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberRepository {

    //조회
    List<Member> findMembers();

    Member findMemberById(Long id);
    //생성

    int insertMember(Member member);
    //수정

    int updateMember(@Param("id") Long id, @Param("member") Member member);
    //삭제

    int deleteMember(Long id);
}
