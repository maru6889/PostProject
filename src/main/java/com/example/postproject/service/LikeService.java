package com.example.postproject.service;

import com.example.postproject.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public boolean toggleLike(Long postId, Long memberId) {
        if (!likeRepository.isLikeExist(postId, memberId)) {
            likeRepository.insertLike(postId, memberId);
            return true;
        } else {
            likeRepository.deleteLike(postId, memberId);
            return false;
        }
    }

    public int getLikeCountByPostId(Long postId) {
        return likeRepository.getLikeCountByPostId(postId);
    }

    public boolean isLikeExist(Long postId, Long memberId) {
        return likeRepository.isLikeExist(postId, memberId);
    }
}
