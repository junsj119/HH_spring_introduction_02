package com.sparta.spring02_test.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.spring02_test.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Setter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Comment extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "COMMENT_ID")
    private Long id;

    @JsonBackReference // 순환참조 방지
    @ManyToOne
    @JoinColumn(name = "MEMO_ID")//양방향 매핑은 수정 어려움
    private Memo memo;

    @Column(nullable = false, name = "RE_CONTENT")
    private String re_content;

    @Column(nullable = true)
    private String commentUser;

    
    // 댓글 작성 시 이용
    public Comment(CommentRequestDto requestDto){
        this.re_content = requestDto.getRe_content();
    }

    public void update(Long memoId, String re_content){
        memo.setId(memoId);
        this.re_content = re_content;
    }
}
