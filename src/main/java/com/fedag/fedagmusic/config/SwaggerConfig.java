package com.fedag.fedagmusic.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "FedAGMusic", version = "1.0",
        description = "Documentation APIs v1.0"))
public class SwaggerConfig {
    public static final String EVERY_DAY_AT_1_AM = "0 0 01 * * *";
}
