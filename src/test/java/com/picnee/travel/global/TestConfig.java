package com.picnee.travel.global;

import com.picnee.travel.global.jwt.properties.JwtProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }
}
