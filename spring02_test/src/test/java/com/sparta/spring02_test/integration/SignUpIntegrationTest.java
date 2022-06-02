package com.sparta.spring02_test.integration;


import com.sparta.spring02_test.domain.Users;
import com.sparta.spring02_test.dto.SignupRequestDto;
import com.sparta.spring02_test.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 포트번호 랜덤
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)       //@order을 사용할 때 필요하다.
public class SignUpIntegrationTest {

    @Autowired
    UserService userService;

    private String username;
    private String password;
    private String password_re;

    private Long userId;

    @BeforeEach
        //하기 전에 얘부터 실행시켜준다.
    void setup() {
        userId = 1L;
        username = "qweqwe123";
        password = "asdasd123";
        password_re = "asdasd123";
    }

    @Test
    public void signUp(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPassword_re(password_re);

        Users user = userService.registerUser(signupRequestDto);

        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        encoder.matches(password, user.getPassword());
        assertEquals(password_re, user.getPassword_re());
    }
}
