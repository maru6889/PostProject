package com.example.postproject.service;

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
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        if (comments.isEmpty()) {
            // 예외 발생
            throw new NoSuchElementException("댓글이 없습니다.");
        }
        return comments;
    }

    public Comment findCommentById(Long id)  {
        Comment comment = commentRepository.findCommentById(id);
        if (comment == null) {
            throw new CommentNotFoundException("해당 댓글이 존재하지 않습니다.");
        }
        return comment;

    }

    //댓글 작성
    public int insertComment(CommentInsertDto dto, Long postId, String loginId) {
        Member member = memberRepository.findMemberByLoginId(loginId);

        int result = commentRepository.insertComment(dto.getContent(), postId, member.getId());

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
