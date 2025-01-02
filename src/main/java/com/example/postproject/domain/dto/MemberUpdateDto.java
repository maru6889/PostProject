package com.example.postproject.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDto {
    private String nickname;
    private String password;
    private String newPassword;
    private String newPasswordCheck;

}

