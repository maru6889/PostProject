package com.example.postproject.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadImage {
    private Long id;
    private String originalFilename;
    private String savedFilename;
    private int filesize;

    private Long postId;
}
