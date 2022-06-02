package com.sparta.spring02_test.repository;

import com.sparta.spring02_test.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMemoId(Long userId);

    List<Comment> findAllByMemoIdOrderByModifiedAtDesc(Long memoId);
}
