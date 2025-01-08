package com.example.postproject.controller;

import com.example.postproject.domain.Comment;
import com.example.postproject.domain.dto.CommentInsertDto;
import com.example.postproject.domain.dto.PostDto;
import com.example.postproject.service.CommentService;
import com.example.postproject.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/insert")
    public String insertComment(@RequestParam("postId") Long postId, @Valid @ModelAttribute("commentInsertDto") CommentInsertDto dto, BindingResult bindingResult, Model model,
                                @RequestParam(required = false, defaultValue = "1", name = "page") int page,
                                @RequestParam(required = false, defaultValue = "5", name = "limit") int limit) {
        if (bindingResult.hasErrors()) {
            PostDto post = postService.findPostWithMemberById(postId);
            int offset = (page - 1) * limit;
            int totalComments = commentService.countCommentsByPostId(postId);
            int totalPages = Math.max((int) Math.ceil((double) totalComments / limit), 1);
            model.addAttribute("post", post);
            model.addAttribute("commentList", commentService.findCommentWithMemberByPostId(postId, limit, offset));
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            return "post/detail";
        }

        commentService.insertComment(dto, postId, "qwer1234");
        return "redirect:/post/detail?id=" + postId;
    }

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
