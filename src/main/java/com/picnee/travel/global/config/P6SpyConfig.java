package com.picnee.travel.global.config;

import com.picnee.travel.global.p6spy.P6SpyEventListener;
import com.picnee.travel.global.p6spy.P6SpyFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6SpyConfig {

    @Bean
    public P6SpyEventListener p6SpyCustomEventListener() {
        return new P6SpyEventListener();
    }

    @Bean
    public P6SpyFormatter p6SpyCustomFormatter() {
        return new P6SpyFormatter();
    }
}