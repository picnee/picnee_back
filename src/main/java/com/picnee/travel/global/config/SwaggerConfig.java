package com.picnee.travel.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

@Configuration
public class SwaggerConfig {

    public static final String ACCESS_TOKEN_SECURITY_SCHEMA_NAME = "AccessToken";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Picnee API 명세서")
                        .description("Picnee에 관한 모든 명세서 정보")
                        .version("1.0.0"))
                // Components 등록
                .components(new Components() // 버튼
                        .addSecuritySchemes(
                                ACCESS_TOKEN_SECURITY_SCHEMA_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(ACCESS_TOKEN_SECURITY_SCHEMA_NAME))
                        )
                .addSecurityItem(new SecurityRequirement() // 스웨거 상에서 매 요청마다 AccessToken을 헤더로 담아줌
                        .addList(ACCESS_TOKEN_SECURITY_SCHEMA_NAME));
    }
}
