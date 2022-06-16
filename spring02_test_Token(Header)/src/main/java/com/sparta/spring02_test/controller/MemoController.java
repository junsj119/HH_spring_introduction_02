package com.sparta.spring02_test.controller;

import com.sparta.spring02_test.domain.Memo;
import com.sparta.spring02_test.dto.MemoRequestDto;
import com.sparta.spring02_test.repository.MemoRepository;
import com.sparta.spring02_test.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController //스프링이 알아서 해줘~
public class MemoController {
    private final MemoRepository memoRepository;
    private final MemoService memoService;

    //메모 작성
    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto){     //RequestBody - 써줘야한다. DTO에 넣어야지~
        Memo memo = new Memo(requestDto);
        return memoRepository.save(memo);
    }

    /*
*
@PostMapping("/api/memos/")
public Memo createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){     //RequestBody - 써줘야한다. DTO에 넣어야지~
    String username = userDetails.getUser().getUsername();
    String password = userDetails.getUser().getPassword();
    String title = requestDto.getTitle();
    String content = requestDto.getContents();

    Memo memo = new Memo(username, password, title, content);

    return memoRepository.save(memo);
}
* */

    @GetMapping("/api/memos")
    public List<Memo> readMemo(){
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(start, end);
        //두 코드 다 최근순정렬
        //return memoRepository.findAllByOrderByModifiedAtDesc();
        //return memoRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));    //조금 더 깊게 알아보자
    }

    @DeleteMapping("/api/memos/{id}")
    public String deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        if(memoService.update(id, requestDto)) {
            memoRepository.deleteById(id);
            return "true";
        }else{
            return "false";
        }
    }

    @PutMapping("/api/memos/{id}")
    public String updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        if(memoService.update(id, requestDto)) {
            return "true";
        }else{
            return "false";
        }
    }
}
