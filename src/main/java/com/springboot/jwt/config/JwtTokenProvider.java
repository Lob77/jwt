package com.springboot.jwt.config;

import com.springboot.jwt.service.UserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private UserDetailsService userDetailsService;

    private String secretKey = "jojedev";
    private final Long tokenValidMilliSecond = 1000L * 60 * 60;

    // secterKey 인코딩
    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        log.info("secretKey 생성: " + secretKey);
    }

    // 토큰 생성
    public String createToken(String username, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilliSecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        log.info("token 생성: " + token);
        return token;
    }

    // 토큰 인증 정보 확인
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsService.loadByUserName(this.getUserName(token));

       return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // 토큰 기반 회원정보 추출
    public String getUserName(String token){
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        log.info("token 기반 회원정보:" + info);
        return info;
    }

    // request에서 token 정보 추출
    public String resolveToken(HttpServletRequest request) {

        log.info("request 헤더에서 token 값 추출:" + request.getHeader("X-AUTH-TOKEN"));
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {

            log.info("토큰 유효성 검사 실패");
            return false;
        }
    }
}
