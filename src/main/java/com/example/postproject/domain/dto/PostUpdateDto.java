package com.example.postproject.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {
    @NotBlank(message = "제목을 작성해주세요.")
    @Size(max = 100, message = "제목은 최대 100자 까지 입력가능합니다.")
    private String title;

    @NotBlank(message = "내용을 작성해주세요.")
    private String content;

    private Long categoryId;
}
