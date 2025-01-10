package com.example.postproject.service;

import com.example.postproject.domain.dto.PostInsertDto;
import com.example.postproject.domain.dto.PostUpdateDto;
import com.example.postproject.exception.PostNotInsertException;
import com.example.postproject.exception.PostNotUpdateException;
import com.example.postproject.exception.PostsNotFoundException;
import com.example.postproject.domain.Member;
import com.example.postproject.domain.Post;
import com.example.postproject.domain.dto.PostDto;
import com.example.postproject.repository.MemberRepository;
import com.example.postproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<Post> findPostsByMemberId(Long memberId) {
        List<Post> posts = postRepository.findPostsByMemberId(memberId);
//        if (posts == null) {
//            throw new PostsNotFoundException("해당 게시글이 존재하지 않습니다.");
//        }
        return posts;
    }

    public List<Post> findPostsWithPaginationAndFilter(String keyword, int offset, int limit) {
        List<Post> posts = postRepository.findPostsWithPaginationAndFilter(keyword, offset, limit);
//        if (posts == null) {
//            throw new PostsNotFoundException("해당 게시글이 존재하지 않습니다.");
//        }
        return posts;
    }

    public List<PostDto> findPostsWithMemberAndPaginationAndFilter(String keyword, int offset, int limit) {
        return postRepository.findPostsWithMemberAndPaginationAndFilter(keyword, offset, limit);
    }

    public Post findPostById(Long id) {
        return postRepository.findPostById(id);
    }

    public PostDto findPostWithMemberById(Long id){
        return postRepository.findPostWithMemberById(id);
    }

    public int countPosts(String keyword){
        return postRepository.countPosts(keyword);
    }

    @Transactional
    public int insertPost(PostInsertDto dto, String loginId){
        Member member = memberRepository.findMemberByLoginId(loginId);
        Post post = Post.builder().title(dto.getTitle())
                .content(dto.getContent())
                .memberId(member.getId())
                .build();
        int result = postRepository.insertPost(post);
//        if (result == 0) {
//            throw new PostNotInsertException("해당 게시글이 등록되지 않았습니다.");
//        }
        return result;
    }

    @Transactional
    public int updatePost(PostUpdateDto dto, Long id) {
        Post post = postRepository.findPostById(id);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        int result = postRepository.updatePost(post);
//        if (result == 0) {
//            throw new PostNotUpdateException("해당 게시글이 수정되지 않았습니다.");
//        }

        return result;
    }

    @Transactional
    public void incrementViews(Long id) {
        postRepository.incrementViews(id);
    }

    @Transactional
    public int deletePost(Long id) {
        return postRepository.deletePost(id);
    }
}
