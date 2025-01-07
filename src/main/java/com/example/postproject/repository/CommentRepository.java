package com.example.postproject.repository;

import com.example.postproject.domain.Comment;
import com.example.postproject.domain.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentRepository {
    //댓글 조회
    List<Comment> findCommentsByPostId(Long postId);

    Comment findCommentById(Long id);

    List<CommentDto> findCommentWithMemberByPostId(@Param("postId") Long postId, @Param("limit") int limit, @Param("offset") int offset);

    int countCommentsByPostId(Long postId);

    //댓글 작성
    int insertComment(Comment comment);

    //댓글 수정
    int updateComment(@Param("id") Long id,@Param("content") String content);

    //댓글 삭제
    int deleteComment(Long id);
}
