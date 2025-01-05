package com.example.postproject.domain.dto;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInsertDto {
    private String nickname;
    private String loginId;
    private String password;
    private String passwordCheck;

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .loginId(loginId)
                .password(password)
                .userRole(UserRole.USER)
                .build();
    }
}
