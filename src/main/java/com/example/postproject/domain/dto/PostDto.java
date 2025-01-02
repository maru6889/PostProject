package com.example.postproject.domain.dto;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private String title;
    private String content;

    public Post toEntity(Long memberId) {
        return Post.builder()
                .title(title)
                .content(content)
                .memberId(memberId)
                .build();
    }
}
