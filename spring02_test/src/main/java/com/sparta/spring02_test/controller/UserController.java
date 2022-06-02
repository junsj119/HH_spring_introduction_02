package com.sparta.spring02_test.controller;


import com.sparta.spring02_test.dto.LoginRequestDto;
import com.sparta.spring02_test.dto.SignupRequestDto;
import com.sparta.spring02_test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/user/signup")
    public String registerUser(@RequestBody SignupRequestDto requestDto) {
        try{
            userService.registerUser(requestDto);
            return "Success";
        }
        catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }

//    // 회원 로그인
//    @PostMapping("/user/login")
//    public String login(@RequestBody LoginRequestDto requestDto) {
//        try{
//            userService.LoginUser(requestDto);
////            SecurityContextHolder.getContext().getAuthentication().getName();
//            return "Success";
//        }
//        catch(IllegalArgumentException e){
//            return e.getMessage();
//        }
//    }

}