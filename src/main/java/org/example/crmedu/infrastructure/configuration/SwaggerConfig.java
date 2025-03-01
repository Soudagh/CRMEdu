package org.example.crmedu.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class defines the OpenAPI specification, including API metadata and security configuration for JWT-based authentication.
 */
@Configuration
public class SwaggerConfig {

  /**
   * Configures the OpenAPI specification with authentication support.
   *
   * @return the configured {@link OpenAPI} instance.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("CRMEduScheme Authentication Service"))
        .addSecurityItem(new SecurityRequirement().addList("CRMEduScheme"))
        .components(new Components().addSecuritySchemes("CRMEduScheme", new SecurityScheme()
            .name("JWTAuth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

  }
}
