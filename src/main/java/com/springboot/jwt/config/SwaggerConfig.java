package com.springboot.jwt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(info());
    }

    @Bean
    public Info info() {
        return new Info()
                .title("Spring Boot JWT")
                .description("JWT 테스트용")
                .version("1.0");
    }
}
