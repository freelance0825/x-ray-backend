package com.freelance.fundoscope_backend.shared.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Fundoscope Service", version = "1.0", description = "Fundoscopic medical application"))
public class SwaggerConfig {

}
