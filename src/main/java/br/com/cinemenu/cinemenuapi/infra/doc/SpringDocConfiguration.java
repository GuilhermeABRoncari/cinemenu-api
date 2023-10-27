package br.com.cinemenu.cinemenuapi.infra.doc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import lombok.Generated;
import org.springframework.context.annotation.Configuration;

@Configuration
@Generated
@SecuritySchemes(
        @io.swagger.v3.oas.annotations.security.SecurityScheme(
                name = "bearerAuth",
                scheme = "bearer",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT"
        )
)
public class SpringDocConfiguration {}
