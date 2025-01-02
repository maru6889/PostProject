package com.example.postproject.controller;

import com.example.postproject.domain.Comment;
import com.example.postproject.domain.dto.CommentInsertDto;
import com.example.postproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

//    @GetMapping("/comment/{id}")
//    public String findCommentsById(@PathVariable Long id){
//        Comment comment = commentService.findCommentById(id);
//        return ""
//    }
//
//    @GetMapping("/{postId}")
//    public ResponseEntity<?> findCommentsByPostId(@PathVariable Long postId) {
//        List<Comment> comments = commentService.findCommentsByPostId(postId);
//        return new ResponseEntity<>(comments, HttpStatus.OK);
//    }
//
//    @PostMapping("/comment/{id}")
//    public ResponseEntity<?> insertComment(@RequestBody CommentInsertDto dto, ){
//
//    }
}
