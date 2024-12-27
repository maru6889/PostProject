package com.example.postproject.repository;

import com.example.postproject.domain.UploadImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UploadImageRepository {

    //조회
    List<UploadImage> findUploadImages(Long postId);

    //생성
    int insertUploadImage(@Param("images") List<UploadImage> images, @Param("postId") Long postId);
    //수정

    int updateUploadImage(@Param("images") List<UploadImage> images, @Param("postId") Long postId);

    //삭제
    int deleteUploadImage(@Param("imageIds") List<Long> imageIds, @Param("postId") Long postId);
}
