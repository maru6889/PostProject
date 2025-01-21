package com.example.postproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDto {
    private String searchType;
    private String keyword;
    private Long categoryId;
    private int offset;
    private int limit;
}
