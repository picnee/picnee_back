package com.picnee.travel.global.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String header;
    private String secret;
    private long accessValidityInSeconds;
    private long refreshValidityInSeconds;
}
