package com.springboot.jwt.controller;

import com.springboot.jwt.config.JwtTokenProvider;
import com.springboot.jwt.entity.User;
import com.springboot.jwt.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name="Test용 API", description = "Jwt 테스트용 API")
public class TestRestContoller {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    @Operation(summary = "회원가입 테스트")
    @Parameter(name="joinUser", description = "회원가입 정보")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 테스트")
    @Parameter(name = "loginUser", description = "로그인 정보")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email")).orElseThrow(() ->
                new UsernameNotFoundException("사용자 정보 없음"));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @PostMapping("/user/resource")
    @Operation(summary = "토큰을 이용한 인증 테스트", security = @SecurityRequirement(name="JWT"))
    public ResponseEntity<String> getProtectedResource() {

        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();

        // 헤더 값 추가
        headers.add("Authorization", "Bearer <your_jwt_token>");

        return new ResponseEntity<>("토큰을 이용한 인증 성공", headers, HttpStatus.OK);
    }
}
