package com.example.postproject.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String loginId;
    private String nickname;
    private String password;
    private String newPassword;
    private String newPasswordCheck;
}
