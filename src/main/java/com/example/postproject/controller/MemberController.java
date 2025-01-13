package com.example.postproject.controller;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.dto.MemberDto;
import com.example.postproject.domain.dto.MemberInsertDto;
import com.example.postproject.domain.dto.MemberLoginDto;
import com.example.postproject.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("loginDto", new MemberLoginDto());
        return "member/login";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
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
            return "member/join";
        }

        if (!dto.getPassword().equals(dto.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "match", "비밀번호가 서로 일치하지 않습니다.");
            return "member/join";
        }

        memberService.insertMember(dto);

        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인을 해주세요.");
        return "redirect:/member/login";
    }

    @GetMapping("/myinfo")
    public String myInfoPage(Authentication auth, Model model) {
        Member member = memberService.findMemberByLoginId(auth.getName());
        MemberDto dto = new MemberDto();
        dto.setNickname(member.getNickname());
        dto.setLoginId(member.getLoginId());

        model.addAttribute("memberDto", dto);
        return "member/myinfo";
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

    @PostMapping("/edit")
    public String editMember(@Valid @ModelAttribute("memberDto") MemberDto memberDto, BindingResult bindingResult, Authentication auth, RedirectAttributes redirectAttributes) {
        Member member = memberService.findMemberByLoginId(auth.getName());
        if (bindingResult.hasErrors()) {
            return "member/edit";
        }
        if (!member.getNickname().equals(memberDto.getNickname()) && memberService.findMemberByNickname(memberDto.getNickname()) != null) {
            bindingResult.rejectValue("nickname", "duplicate", "이미 존재하는 닉네임입니다.");
            return "member/edit";
        }
        if (!encoder.matches(memberDto.getPassword(), member.getPassword())) {
            bindingResult.rejectValue("password", "notequal", "기존 비밀번호가 일치하지 않습니다.");
            return "member/edit";
        }
        if (memberDto.getNewPassword() != null && !memberDto.getNewPassword().isBlank()) {
            if (!memberDto.getNewPassword().equals(memberDto.getNewPasswordCheck())) {
                bindingResult.rejectValue("newPassword", "notequal", "새로운 비밀번호가 서로 일치하지 않습니다.");
                return "member/edit";
            }
        }

        memberService.updateMember(memberDto, member.getLoginId());
        redirectAttributes.addFlashAttribute("message", "회원 정보가 정상적으로 수정되었습니다.");
        return "redirect:/member/myinfo";

    }

    @GetMapping("/delete")
    public String deletePage(Authentication auth, Model model) {
        String loginId = auth.getName();
        Member member = memberService.findMemberByLoginId(loginId);
        MemberDto memberDto = new MemberDto();
        memberDto.setLoginId(loginId);
        memberDto.setNickname(member.getNickname());

        model.addAttribute("memberDto", memberDto);
        return "member/delete";
    }

    @PostMapping("/delete")
    public String deleteMember(@Valid @ModelAttribute("memberDto") MemberDto memberDto, BindingResult bindingResult, Authentication auth, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Member member = memberService.findMemberByLoginId(auth.getName());
        if (!encoder.matches(memberDto.getPassword(), member.getPassword())) {
            bindingResult.rejectValue("password", "notequal", "비밀번호가 일치하지 않습니다.");
            return "member/delete";
        }
        memberService.deleteMember(member.getLoginId());

        HttpSession session =  request.getSession(false); //이미 존재하는 세션이 있을 때: 해당 세션을 반환.
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        redirectAttributes.addFlashAttribute("message", "회원탈퇴가 완료되었습니다.");
        return "redirect:/";
    }
}
