package com.example.postproject.service;

import com.example.postproject.domain.dto.CommentDto;
import com.example.postproject.exception.CommentNotFoundException;
import com.example.postproject.domain.Comment;
import com.example.postproject.domain.Member;
import com.example.postproject.domain.dto.CommentInsertDto;
import com.example.postproject.repository.CommentRepository;
import com.example.postproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    //댓글 조회
    public List<Comment> findCommentsByPostId(Long postId){
        return commentRepository.findCommentsByPostId(postId);
    }

    public Comment findCommentById(Long id)  {
        Comment comment = commentRepository.findCommentById(id);
        if (comment == null) {
            throw new CommentNotFoundException("해당 댓글이 존재하지 않습니다.");
        }
        return comment;

    }

    public List<CommentDto> findCommentWithMemberByPostId(Long postId, int limit, int offset) {
        return commentRepository.findCommentWithMemberByPostId(postId, limit, offset);
    }

    public int countCommentsByPostId(Long postId) {
        return commentRepository.countCommentsByPostId(postId);
    }

    //댓글 작성
    public int insertComment(CommentInsertDto dto, Long postId, String loginId) {
        Member member = memberRepository.findMemberByLoginId(loginId);
        Comment comment = Comment.builder()
                .content(dto.getContent())
                .memberId(member.getId())
                .postId(postId)
                .build();

        int result = commentRepository.insertComment(comment);

        return result;
    }

    //댓글 수정
    public int updateComment(Long id, String content, String loginId){
        Member member = memberRepository.findMemberByLoginId(loginId);
        Comment comment = commentRepository.findCommentById(id);
        if (comment == null || member == null) {
            return 0;
        }
        return commentRepository.updateComment(id, content);
    }

    public int deleteComment(Long id, String loginId) {
        Member member = memberRepository.findMemberByLoginId(loginId);
        Comment comment = commentRepository.findCommentById(id);
        if (comment == null || member == null) {
            return 0;
        }
        return commentRepository.deleteComment(id);
    }
}
