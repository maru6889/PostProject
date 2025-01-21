package com.example.postproject.controller;

import com.example.postproject.domain.Likes;
import com.example.postproject.domain.Member;
import com.example.postproject.service.LikeService;
import com.example.postproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;
    private final MemberService memberService;
    //좋아요 생성 및 추가 토글 방식 구현
    @PostMapping("/toggle")
    public Map<String, Object> toggleLike(@RequestBody Map<String, Object> requestData, Authentication auth) {
        Long postId = Long.valueOf(requestData.get("postId").toString());
        Member member = memberService.findMemberByLoginId(auth.getName());
        Map<String, Object> responseData = new HashMap<>();
        Long memberId = member.getId();

        boolean isLiked = likeService.toggleLike(postId, memberId);
        int likeCount = likeService.getLikeCountByPostId(postId);

        responseData.put("success", true);
        responseData.put("isLiked", isLiked);
        responseData.put("likeCount", likeCount);

        return responseData;
    }



}
