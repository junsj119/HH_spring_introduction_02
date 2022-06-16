package com.sparta.spring02_test.service;

import com.sparta.spring02_test.domain.Comment;
import com.sparta.spring02_test.domain.Memo;
import com.sparta.spring02_test.dto.CommentRequestDto;
import com.sparta.spring02_test.repository.CommentRepository;
import com.sparta.spring02_test.repository.MemoRepository;
import com.sparta.spring02_test.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor  //final이나 NUllof?? 애들을 생성자를 만들어준다.
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemoRepository memoRepository;

    //댓글 목록 조회
    public List<Comment> get_Comment(Long memoId){
        //return commentRepository.findAllByMemoId(memoId);
        return commentRepository.findAllByMemoIdOrderByModifiedAtDesc(memoId);
    }

    //댓글 작성
    public void create_Comment(Long memoId, CommentRequestDto requestDto, UserDetailsImpl userDetails){

        if(userDetails == null)
            throw new IllegalArgumentException("로그인이 필요합니다.");

        Comment comment = new Comment(requestDto);

        Memo memo = memoRepository.findById(memoId).orElseThrow(()->
                new IllegalArgumentException("게시글이 없습니다."));

        comment.setMemo(memo);
        comment.setCommentUser(userDetails.getUsername());

        //댓글창이 NULL 일 때
        if(requestDto.getRe_content().equals(""))
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");

        commentRepository.save(comment);
    }

    //댓글 수정
    @Transactional
    public void update_Comment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("댓글이 없습니다.")
        );

        comment.setCommentUser(userDetails.getUsername());

        //방금 저장한 이름이랑 수정을 신청한 이름이랑 같다면
        if(comment.getCommentUser().equals(userDetails.getUsername())){
            comment.setRe_content(requestDto.getRe_content());
            commentRepository.save(comment);
        }
        else{
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

    }

    //댓글 삭제
    public void delete_Comment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("댓글이 없습니다.")
        );
        comment.setCommentUser(userDetails.getUsername());

        //방금 저장한 이름이랑 수정을 신청한 이름이랑 같다면
        if(comment.getCommentUser().equals(userDetails.getUsername())){
            commentRepository.deleteById(commentId);
        }
        else{
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }
    }
}
