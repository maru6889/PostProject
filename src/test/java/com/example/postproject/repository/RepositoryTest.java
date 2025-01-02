package com.example.postproject.repository;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * given(준비): 어떠한 데이터가 준비되었을 때
 * when(실행): 어떠한 함수를 실행하면
 * then(검증): 어떠한 결과가 나와야 한다.
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.yml")
class RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    public void insertMember(){
        Member member = new Member();
        member.setNickname("DYC");
        member.setLoginId("qwer1234");
        member.setPassword("qwer1234");
        member.setUserRole(UserRole.USER);
        memberRepository.insertMember(member);

        List<Member> members = memberRepository.findMembers();
        Assertions.assertEquals(1, members.size());
        Member findMember = members.get(0);
        Assertions.assertEquals(1L, findMember.getId());
    }

    /*@Test
    public void findMemberById(){
        Member member = memberRepository.findMemberById(1L);
        Assertions.assertEquals(1L, member.getId());
    }*/
}