package com.example.postproject.service;

import com.example.postproject.domain.Comment;
import com.example.postproject.domain.dto.CommentDto;
import com.example.postproject.domain.dto.CommentInsertDto;
import com.example.postproject.exception.CommentNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @Transactional
    public void insertComment() {
        CommentInsertDto dto = new CommentInsertDto();
        dto.setContent("안녕하세요. 처음 뵙겠습니다1");


        int result = commentService.insertComment(dto, 1L, "qwer1234");

        assertThat(result).isEqualTo(1);
    }

    @Test
    public void findCommentsByPostId() {
        List<Comment> comments = commentService.findCommentsByPostId(1L);
        assertThat(comments.size()).isEqualTo(3);
    }

    @Test
    public void findCommentById() {
        Comment comment = commentService.findCommentById(1L);
        assertThat(comment.getId()).isEqualTo(1L);
    }

    @Test
    public void findCommentWithMemberByPostId() {
        List<CommentDto> comments = commentService.findCommentWithMemberByPostId(106L, 10, 0);
        assertThat(comments.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void updateComment() {
        int result = commentService.updateComment(1L, "안녕", "qwer1234");
        assertThat(result).isEqualTo(1);

        Comment comment = commentService.findCommentById(1L);
        assertThat(comment.getContent()).isEqualTo("안녕");
    }

    @Test
    @Transactional
    public void deleteComment() {
        int result = commentService.deleteComment(1L, "qwer1234");

        assertThat(result).isEqualTo(1);

        org.junit.jupiter.api.Assertions.assertThrows(CommentNotFoundException.class, () -> {
            commentService.findCommentById(1L);
        });

    }
}