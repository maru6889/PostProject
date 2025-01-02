package com.example.postproject.repository;

import com.example.postproject.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentRepository {
    //댓글 조회
    List<Comment> findCommentsByPostId(Long postId);

    Comment findCommentById(Long id);

    //댓글 작성
    int insertComment(@Param("content") String content, @Param("postId") Long postId, @Param("memberId") Long memberId);

    //댓글 수정
    int updateComment(@Param("id") Long id,@Param("content") String content);

    //댓글 삭제
    int deleteComment(Long id);
}
