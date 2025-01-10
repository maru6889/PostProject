package com.example.postproject.domain.dto;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInsertDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(max = 20, min = 8, message = "최소 8자, 최대 20자의 아이디를 입력해주세요.")
    private String nickname;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 10, min = 2, message = "최소 2자, 최대 10자의 닉네임를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "최소 8자, 최대 20자의 비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .nickname(nickname)
                .loginId(loginId)
                .password(encodedPassword)
                .userRole(UserRole.ROLE_USER)
                .build();
    }
}
