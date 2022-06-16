package com.sparta.spring02_test.service;

import com.sparta.spring02_test.domain.Memo;
import com.sparta.spring02_test.dto.MemoRequestDto;
import com.sparta.spring02_test.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor  //final이나 NUllof?? 애들을 생성자를 만들어준다.
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    @Transactional      //sql쿼리가 일어나야 되는것을 스프링에게 알려주는 친구이다
    public boolean update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        if(memo.getPassword().equals(requestDto.getPassword())){
            memo.update(requestDto);
            return true;
        }
        else{
            return false;
        }
    }
}