package com.example.postproject.controller;

import com.example.postproject.domain.Member;
import com.example.postproject.domain.Post;
import com.example.postproject.domain.dto.*;
import com.example.postproject.service.CommentService;
import com.example.postproject.service.LikeService;
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
    private final LikeService likeService;

    @GetMapping("/search")
    public String searchPost(Model model,
                             @RequestParam(required = false, name = "keyword") String keyword,
                             @RequestParam(required = false, defaultValue = "all", name = "searchType") String searchType,
                             @RequestParam(required = false, name = "categoryId") Long categoryId,
                             @RequestParam(required = false, defaultValue = "1", name = "page") int page,
                             @RequestParam(required = false, defaultValue = "10", name = "limit") int limit){
        PostSearchDto searchDto = new PostSearchDto();
        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
        searchDto.setSearchType(searchType);
        searchDto.setCategoryId(categoryId);
        searchDto.setKeyword(keyword);
        searchDto.setOffset(offset);
        searchDto.setLimit(limit);

        List<PostDto> posts = postService.findPostBySearchCondition(searchDto);
        int totalPosts = postService.countPostsBySearchCondition(searchDto);
        int totalPages = Math.max((int) Math.ceil((double) totalPosts / limit), 1);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postSearchDto", searchDto);

        return "post/list";
    }

    @GetMapping("/list")
    public String getAllPost(
            Model model,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, name = "categoryId") Long categoryId,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "limit") int limit)  {

        PostSearchDto searchDto = new PostSearchDto();
        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
        searchDto.setCategoryId(categoryId);
        searchDto.setKeyword(keyword);
        searchDto.setOffset(offset);
        searchDto.setLimit(limit);
        List<PostDto> posts = postService.findPostsWithMemberAndPaginationAndFilter(searchDto);

        int totalPosts = postService.countPostsBySearchCondition(searchDto);

        int totalPages = Math.max((int) Math.ceil((double) totalPosts / limit), 1);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postSearchDto", searchDto);

        return "post/list";
    }

    /**
     * @param model:   게시글 리스트
     * @param keyword: 검색할 단어
     * @param limit:   한번에 가져올 데이터 개수
     * @param page:    현재 페이지 번호
     * @return
     */
    @GetMapping("/list/notice")
    public String postListNoticePage(
            Model model,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "limit") int limit) {

        Long categoryId = 1L;
        PostSearchDto searchDto = new PostSearchDto();
        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
        searchDto.setKeyword(keyword);
        searchDto.setOffset(offset);
        searchDto.setLimit(limit);
        searchDto.setCategoryId(categoryId);
        List<PostDto> posts = postService.findPostsWithMemberAndPaginationAndFilter(searchDto);

        int totalPosts = postService.countPostsBySearchCondition(searchDto);

        int totalPages = Math.max((int) Math.ceil((double) totalPosts / limit), 1);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postSearchDto", searchDto);

        return "post/list";
    }

    @GetMapping("/list/community")
    public String postListCommunityPage(
            Model model,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "limit") int limit) {

        Long categoryId = 2L;
        PostSearchDto searchDto = new PostSearchDto();
        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
        searchDto.setKeyword(keyword);
        searchDto.setOffset(offset);
        searchDto.setLimit(limit);
        searchDto.setCategoryId(categoryId);
        List<PostDto> posts = postService.findPostsWithMemberAndPaginationAndFilter(searchDto);

        int totalPosts = postService.countPostsBySearchCondition(searchDto);

        int totalPages = Math.max((int) Math.ceil((double) totalPosts / limit), 1);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postSearchDto", searchDto);

        return "post/list";
    }

    @GetMapping("/list/question")
    public String postListQuestionPage(
            Model model,
            @RequestParam(required = false, name = "keyword") String keyword,
            @RequestParam(required = false, defaultValue = "1", name = "page") int page,
            @RequestParam(required = false, defaultValue = "10", name = "limit") int limit) {

        Long categoryId = 3L;
        PostSearchDto searchDto = new PostSearchDto();
        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
        searchDto.setKeyword(keyword);
        searchDto.setOffset(offset);
        searchDto.setLimit(limit);
        searchDto.setCategoryId(categoryId);
        List<PostDto> posts = postService.findPostsWithMemberAndPaginationAndFilter(searchDto);

        int totalPosts = postService.countPostsBySearchCondition(searchDto);

        int totalPages = Math.max((int) Math.ceil((double) totalPosts / limit), 1);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postSearchDto", searchDto);

        return "post/list";
    }

    @GetMapping("/detail")
    public String postDetailPage(Model model, @RequestParam(name = "id") Long id,
                                 @RequestParam(required = false, name = "page", defaultValue = "1") int page,
                                 @RequestParam(required = false, name = "limit", defaultValue = "5") int limit,
                                 Authentication auth) {



        postService.incrementViews(id);
        boolean userLiked = false;
        int likeCount = likeService.getLikeCountByPostId(id);

        if (auth != null && auth.isAuthenticated()) {
            String loginId = auth.getName();
            Long memberId = memberService.findMemberByLoginId(loginId).getId();
            userLiked = likeService.isLikeExist(id, memberId);
        }

        int offset = (page - 1) * limit; //조회할 데이터의 시작 위치
//        log.info("id: {}", id);
        PostDto dto = postService.findPostWithMemberById(id);
//        log.info("dto: {}", dto.getCategoryName());
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
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("userLiked", userLiked);
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

        Member member = memberService.findMemberByLoginId(auth.getName());
        if (bindingResult.hasErrors()) {
            return "post/write";
        }

        if (dto.getCategoryId() == 1L && !member.getUserRole().name().equals("ROLE_ADMIN")) {
            bindingResult.reject("categoryError", "공지사항은 관리자만 작성할 수 있습니다.");
            return "post/write";
        }

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
