package com.example.postproject.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeRepository {
    int getLikeCountByPostId(Long postId);

    boolean isLikeExist(@Param("postId") Long postId, @Param("memberId") Long memberId);

    void insertLike(@Param("postId") Long postId, @Param("memberId") Long memberId);

    void deleteLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
}
