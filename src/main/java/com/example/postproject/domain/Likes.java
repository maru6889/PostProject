package com.example.postproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Likes {
    private Long id;
    private LocalDateTime createdAt;
    private Long memberId;
    private Long postId;
}
