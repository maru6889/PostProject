package com.example.postproject.controller;

import com.example.postproject.domain.Post;
import com.example.postproject.domain.dto.PostDto;
import com.example.postproject.domain.dto.PostSearchDto;
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

        int totalPages = (int) Math.ceil((double) totalPosts / limit);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postSearchDto", new PostSearchDto(keyword));

        return "post/list";
    }
}
