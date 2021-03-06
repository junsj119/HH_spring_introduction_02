package com.sparta.spring02_test.service;


import com.sparta.spring02_test.domain.Users;
import com.sparta.spring02_test.dto.LoginRequestDto;
import com.sparta.spring02_test.dto.SignupRequestDto;
import com.sparta.spring02_test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    //복호화, 암호화
    private final BCryptPasswordEncoder encoder;
    private final PasswordEncoder passwordEncoder;
    

    //회원가입
    public Users registerUser(SignupRequestDto requestDto){
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<Users> found = userRepository.findByUsername(username);
        //- 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지
        if (found.isPresent())
            throw new IllegalArgumentException("중복된 닉네임입니다.");

        //- 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
        String pattern = "^[a-zA-Z0-9]*$";
        if(requestDto.getUsername().length() < 3 && Pattern.matches(pattern, requestDto.getUsername()))
            throw new IllegalArgumentException("닉네임은 3자리 이상 입력해주세요.");

        //패스워드
        String password = passwordEncoder.encode(requestDto.getPassword());
        String password_re = requestDto.getPassword_re();

        //- 비밀번호 확인은 비밀번호와 정확하게 일치하기
        //if(!requestDto.getPassword().equals(password_re))
        if(!encoder.matches(password_re, password))
            throw new IllegalArgumentException("비밀번호 확인을 다시 해주세요.");

        //- 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기
        if(requestDto.getPassword().length() < 4 || requestDto.getPassword().contains(username))
            throw new IllegalArgumentException("비밀번호 4자리 이상, 혹은 닉네임과 같은 값을 사용할 수 없습니다.");

        //데이터 저장
        Users user = new Users(username, password, password_re);
        userRepository.save(user);
        return user;
    }

    //로그인 (시큐리티가 해준다)
//    public boolean LoginUser(LoginRequestDto requestDto) {
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        Optional<Users> nick = userRepository.findByUsername(username);
//        Optional<Users> pass = userRepository.findByPassword(password);
//        if (nick.isPresent())
//        {
//
//        } else
//            throw new IllegalArgumentException("아이디를 확인해주세요.");
//
//        if (pass.isPresent())
//        {} else
//            throw new IllegalArgumentException("비밀번호를 확인해주세요.");
//
//        return true;
//    }
}