package com.example.postproject.service;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.dto.MemberInsertDto;
import com.example.postproject.domain.dto.MemberUpdateDto;
import com.example.postproject.exception.MemberNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void insertMember() {
        MemberInsertDto dto = new MemberInsertDto();
        dto.setLoginId("qwer12345");
        dto.setPassword("qwer1234");
        dto.setNickname("qwer12345");
        dto.toEntity();
        int result = memberService.insertMember(dto);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @Transactional
    public void findMemberByLoginId(){
        String loginId = "qwer1234";
        Member member = memberService.findMemberByLoginId(loginId);
        Long id = member.getId();
        String nickname = member.getNickname();
        String loginId2 = member.getLoginId();
        assertThat(id).isEqualTo(6L);
        assertThat(nickname).isEqualTo("qwer1234");
//        assertThat(loginId2).isEqualTo("qwer1234");
    }

    @Test
    public void findMembers(){
        List<Member> members = memberService.findMembers();

        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void updateMember(){
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setNewPassword("qwerqwer");
        dto.setNickname("qwer123456");
        String loginId = "qwer1234";
        int result = memberService.updateMember(dto, loginId);
        assertThat(result).isEqualTo(1);
        Member member = memberService.findMemberByLoginId(loginId);
        assertThat(member.getPassword()).isEqualTo("qwerqwer");
        assertThat(member.getNickname()).isEqualTo("qwer123456");
    }

    @Test
    @Transactional
    public void deleteMember(){
        String loginId = "qwer1234";
        String password = "qwer1234";
        memberService.deleteMember(loginId, password);

        assertThrows(MemberNotFoundException.class, () -> {
            Member member = memberService.findMemberByLoginId(loginId);
        });

    }
}