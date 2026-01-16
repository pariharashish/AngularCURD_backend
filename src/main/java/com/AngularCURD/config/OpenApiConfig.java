package com.AngularCURD.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Employee Management API",
        version = "v1",
        description = "API for managing employee and their profiles",
        termsOfService = "Terms of service",
        contact = @Contact(
            name = "API Support",
            email = "support@example.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://springdoc.org"
        )
    ),
    servers = {
        @Server(url = "https://api.example.com", description = "Production server"),
        @Server(url = "https://dev-api.example.com", description = "Development server")
    },
    security = {
        @SecurityRequirement(name = "bearerAuth")
    }
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("Employee Management System")
                                .version("v1.0")
                                .description("RESTful API for user management operations")
                                .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("API Team")
                                .email("api-team@example.com"))
                );
    }
}
