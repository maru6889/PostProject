package com.example.postproject.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInsertDto {

    @NotBlank(message = "댓글을 작성해주세요.")
    @Size(max = 50, message = "댓글은 최대 50자 까지 입력가능합니다.")
    private String content;
}
