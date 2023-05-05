package com.milosz000.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Milosz",
                        email = "mazur.milosz000@gmail.com",
                        url = "https://github.com/mazurmilosz000"
                ),
                description = "OpenApi documentation for auth-service microservice",
                title = "OpenApi - auth-service",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
