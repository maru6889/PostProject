package com.example.postproject.controller;

import com.example.postproject.domain.Post;
import com.example.postproject.domain.dto.CommentInsertDto;
import com.example.postproject.domain.dto.PostDto;
import com.example.postproject.domain.dto.PostSearchDto;
import com.example.postproject.service.CommentService;
import com.example.postproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    /**
     *
     * @param model: 게시글 리스트
     * @param keyword: 검색할 단어
     * @param limit: 한번에 가져올 데이터 개수
     * @param page: 현재 페이지 번호
     * @return
     */
    @GetMapping("/list")
    public String postListPage(
            Model model,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "limit") int limit) {

        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
        List<PostDto> posts = postService.findPostsWithMemberAndPaginationAndFilter(keyword, offset, limit);

        int totalPosts = postService.countPosts(keyword);

        int totalPages = Math.max((int) Math.ceil((double) totalPosts / limit), 1);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postSearchDto", new PostSearchDto(keyword));

        return "post/list";
    }

    @GetMapping("/detail")
    public String postDetailPage(Model model, @RequestParam(name = "id") Long id,
                                 @RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                 @RequestParam(required = false, name = "limit", defaultValue = "5") int limit){


        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
        PostDto dto = postService.findPostWithMemberById(id);
        int totalComments = commentService.countCommentsByPostId(id);
        int totalPages = Math.max((int) Math.ceil((double) totalComments / limit), 1);

        model.addAttribute("post", dto);
        model.addAttribute("commentInsertDto", new CommentInsertDto());
        model.addAttribute("commentList", commentService.findCommentWithMemberByPostId(id, limit, offset));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "post/detail";
    }
}
