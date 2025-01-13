package com.example.postproject.domain.dto;

import com.example.postproject.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String loginId;
    private String nickname;
}
