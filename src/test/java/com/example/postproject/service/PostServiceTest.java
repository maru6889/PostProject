package com.example.postproject.service;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.Post;
import com.example.postproject.domain.dto.PostDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    public void insertPost(){
//        assertThat(member).isNull();
        PostDto dto = new PostDto();
        dto.setTitle("안녕하세요. 반갑습니다.");
        dto.setContent("안녕하세요 반갑습니다. \n제 이름은 최동연입니다.");
        dto.toEntity(6L);
        int result = postService.insertPost(dto, "qwer1234");

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void findPostsByMemberId() {
        List<Post> posts = postService.findPostsByMemberId(6L);
        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    public void findPostsWithPaginationAndFilter(){
        List<Post> posts = postService.findPostsWithPaginationAndFilter("안녕", 0, 10);
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.getFirst().getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void updatePost() {
        PostDto dto = new PostDto();
        dto.setTitle("안녕하세요");
        dto.setContent("...");
        int result = postService.updatePost(dto, 1L);
        assertThat(result).isEqualTo(1);
        Post post = postService.findPostById(1L);
        assertThat(post.getTitle()).isEqualTo("안녕하세요");
        assertThat(post.getContent()).isEqualTo("...");
    }

    @Test
    @Transactional
    public void deletePost() {
        int result = postService.deletePost(1L);
        assertThat(result).isEqualTo(1);
        Post post = postService.findPostById(1L);
        assertThat(post).isNull();
    }
}