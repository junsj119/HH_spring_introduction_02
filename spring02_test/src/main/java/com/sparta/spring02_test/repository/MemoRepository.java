package com.sparta.spring02_test.repository;

import com.sparta.spring02_test.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    //findAll By OrderBy ModifiedAt Desc    -> ModifiedAt 얘는 Timestamped에 있음
    List<Memo> findAllByOrderByModifiedAtDesc();    //다 찾아서 순서대로 정렬렬해주는데 수정된 날짜를 기준으로 내림차순
    List<Memo> findAllByModifiedAtBetweenOrderByModifiedAtDesc(LocalDateTime start, LocalDateTime end);
}
