package com.springboot.jwt.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(info())
                .addSecurityItem(new SecurityRequirement().addList("JWT")) // 보안 요구 사항 추가 (JWT 인증)
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer") // 인증 방식: bearer
                            .bearerFormat("JWT") // JWT 토큰 형식
                            .in(SecurityScheme.In.HEADER) // 인증 정보는 HTTP 헤더에 포함
                            .name("Authorization")
                ));
    }

    @Bean
    public Info info() {
        return new Info()
                .title("Spring Boot JWT")
                .description("JWT 테스트용")
                .version("1.0");
    }
}
