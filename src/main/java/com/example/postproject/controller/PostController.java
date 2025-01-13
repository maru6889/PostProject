package com.example.postproject.controller;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.Post;
import com.example.postproject.domain.dto.*;
import com.example.postproject.service.CommentService;
import com.example.postproject.service.MemberService;
import com.example.postproject.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    /**
     * @param model:   게시글 리스트
     * @param keyword: 검색할 단어
     * @param limit:   한번에 가져올 데이터 개수
     * @param page:    현재 페이지 번호
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
                                 @RequestParam(required = false, name = "limit", defaultValue = "5") int limit) {


        postService.incrementViews(id);

        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
//        log.info("id: {}", id);
        PostDto dto = postService.findPostWithMemberById(id);
//        log.info("dto: {}", dto);
//        log.info("updatedAt: {}", dto.getUpdatedAt());
//        log.info("dto.nickname: {}", dto.getNickname());
//        log.info("dto.loginId: {}", dto.getLoginId());
        int totalComments = commentService.countCommentsByPostId(id);
        int totalPages = Math.max((int) Math.ceil((double) totalComments / limit), 1);

        model.addAttribute("post", dto);
        model.addAttribute("commentInsertDto", new CommentInsertDto());
        model.addAttribute("commentList", commentService.findCommentWithMemberByPostId(id, limit, offset));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "post/detail";
    }

    @GetMapping("/write")
    public String postWritePage(Model model, Authentication auth) {
        PostInsertDto dto = new PostInsertDto();
        model.addAttribute("dto", dto);
        return "post/write";
    }

    @PostMapping("/write")
    public String savePost(@Valid @ModelAttribute("dto") PostInsertDto dto, BindingResult bindingResult, Authentication auth) {

        if (bindingResult.hasErrors()) {
            return "post/write";
        }

        Member member = memberService.findMemberByLoginId(auth.getName());
        postService.insertPost(dto, member.getLoginId()); // 추후 로그인 구현 후 변경 필수
        return "redirect:/post/list";
    }

    @GetMapping("/edit")
    public String editPostPage(Model model, @RequestParam("id") Long postId) {

        Post post = postService.findPostById(postId);
        PostUpdateDto dto = new PostUpdateDto();
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        model.addAttribute("postId", postId);
        model.addAttribute("dto", dto);
        return "/post/edit";
    }


    @PostMapping("/edit")
    public String editPost(@RequestParam("id") Long postId, @Valid @ModelAttribute("dto") PostUpdateDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postId", postId);
            return "/post/edit";
        }

        postService.updatePost(dto, postId);

        return "redirect:/post/detail?id=" + postId;
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam("id") Long postId) {
        postService.deletePost(postId);
        return "redirect:/post/list";
    }
}
