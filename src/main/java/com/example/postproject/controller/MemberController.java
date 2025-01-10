package com.example.postproject.controller;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.dto.MemberDto;
import com.example.postproject.domain.dto.MemberInsertDto;
import com.example.postproject.domain.dto.MemberLoginDto;
import com.example.postproject.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("memberInsertDto", new MemberInsertDto());
        return "member/join";
    }

    @PostMapping("/join")
    public String insertMember(@Valid @ModelAttribute("memberInsertDto") MemberInsertDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        Member member = memberService.findMemberByLoginId(dto.getLoginId());

        if (member != null) {
            bindingResult.rejectValue("loginId", "duplicate", "이미 사용중인 아이디입니다.");
            return "member/join";
        }

        member = memberService.findMemberByNickname(dto.getNickname());

        if (member != null) {
            bindingResult.rejectValue("nickname", "duplicate", "이미 사용중인 닉네임입니다.");
        }

        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "match", "비밀번호가 서로 일치하지 않습니다.");
            return "member/join";
        }

        memberService.insertMember(dto);

        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 로그인을 해주세요.");
        return "redirect:/member/login";
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
