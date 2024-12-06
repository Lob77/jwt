package com.springboot.jwt.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/user").hasRole("USER")
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
