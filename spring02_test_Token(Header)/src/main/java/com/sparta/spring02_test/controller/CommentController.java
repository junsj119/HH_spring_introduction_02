package com.sparta.spring02_test.controller;

import com.sparta.spring02_test.domain.Comment;
import com.sparta.spring02_test.domain.Users;
import com.sparta.spring02_test.dto.CommentRequestDto;
import com.sparta.spring02_test.repository.CommentRepository;
import com.sparta.spring02_test.security.UserDetailsImpl;
import com.sparta.spring02_test.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    //댓글 목록 조회
    @GetMapping("/api/comment/{id}")
    public List<Comment> readComment(@PathVariable String id){
        return commentService.get_Comment(Long.parseLong(id));
    }

    //댓글 작성
    @PostMapping("/api/comment/{id}")
    public String writeComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long memoId = id;
        try{
            commentService.create_Comment(memoId, requestDto, userDetails);
            return "Success";
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }

    //댓글 수정
    @PutMapping("/api/comment/{id}")
    public String updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long commentId = id;
        try{
            commentService.update_Comment(commentId, requestDto, userDetails);
            return "Success";
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }

    //댓글 삭제
    @DeleteMapping("/api/comment/{id}")
    public String deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long commentId = id;
        try{
            commentService.delete_Comment(commentId, userDetails);
            return "Success";
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }
}
