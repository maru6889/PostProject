package com.example.postproject.repository;

import com.example.postproject.domain.Post;
import com.example.postproject.domain.dto.PostDto;
import com.example.postproject.domain.dto.PostSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostRepository {

    //조회
    List<Post> findPostsByMemberId(Long memberId);

    List<Post> findPostsWithPaginationAndFilter(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);

    List<PostDto> findPostsWithMemberAndPaginationAndFilter(PostSearchDto searchDto);

    List<PostDto> findPostBySearchCondition(PostSearchDto searchDto);
    Post findPostById(Long id);

    PostDto findPostWithMemberById(Long id);

    int countPosts(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

    int countPostsBySearchCondition(PostSearchDto searchDto);

    //생성
    int insertPost(Post post);

    //수정
    int updatePost(Post post);

    void incrementViews(Long id);

    //삭제
    int deletePost(Long id);
}
