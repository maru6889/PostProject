package com.example.postproject.controller;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.dto.MemberDto;
import com.example.postproject.domain.dto.MemberInsertDto;
import com.example.postproject.domain.dto.MemberLoginDto;
import com.example.postproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage(Model model){

        model.addAttribute("loginDto", new MemberLoginDto());
        return "member/login";
    }

    @GetMapping("/join")
    public String joinPage(Model model){
        model.addAttribute("memberDto", new MemberInsertDto());
        return "member/join";
    }

    @GetMapping("/edit")
    public String editPage(Authentication auth, Model model) {
        String loginId = auth.getName();
        Member member = memberService.findMemberByLoginId(loginId);
        MemberDto memberDto = new MemberDto();
        memberDto.setLoginId(loginId);
        memberDto.setNickname(member.getNickname());

        model.addAttribute("memberDto", memberDto);
        return "member/edit";
    }

    @GetMapping("/delete")
    public String deletePage(Authentication auth, Model model){
        String loginId = auth.getName();
        Member member = memberService.findMemberByLoginId(loginId);
        MemberDto memberDto = new MemberDto();
        memberDto.setLoginId(loginId);
        memberDto.setNickname(member.getNickname());

        model.addAttribute("memberDto", memberDto);
        return "member/delete";
    }


}
