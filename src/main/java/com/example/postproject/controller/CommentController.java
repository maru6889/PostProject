package com.example.postproject.controller;

import com.example.postproject.domain.Comment;
import com.example.postproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateComment(@RequestBody Map<String, Object> requestData) {

        Long commentId = Long.valueOf(requestData.get("id").toString());
        String content = requestData.get("content").toString();

        int result = commentService.updateComment(commentId, content, "qwer1234");

        Map<String, Object> responseData = new HashMap<>();
        if (result == 0) {
            responseData.put("success", false);
        } else {
            responseData.put("success", true);
        }

        return responseData;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteComment(@RequestBody Map<String, Object> requestData) {
        Long commentId = Long.valueOf(requestData.get("id").toString());

        Comment comment = commentService.findCommentById(commentId);
        Long postId = comment.getPostId();
        log.info("postId: {}", postId);
        int result = commentService.deleteComment(commentId, "qwer1234");
        Map<String, Object> responseData = new HashMap<>();
        if (result == 0) {
            responseData.put("success", false);
        } else {
            responseData.put("success", true);
            responseData.put("redirectUrl", "/post/detail?id=" + postId);
        }

        return responseData;
    }
}
