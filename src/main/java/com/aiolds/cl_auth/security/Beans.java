package com.aiolds.cl_auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Beans {

    private final JwtFilter jwtFilter;
    @Bean
    public JwtFilter jwtFilter() {
        return jwtFilter;
    }
}
