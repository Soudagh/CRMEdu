package org.example.crmedu.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.crmedu.infrastructure.auth.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class defines security policies, request authorization rules, and integrates JWT filtering.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  /**
   * Defines security rules and integrates JWT authentication.
   *
   * @param http the {@link HttpSecurity} configuration.
   * @return the configured security filter chain.
   */
  @Bean
  @SneakyThrows
  public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/error").permitAll()
            .requestMatchers("api/v1/auth/signup", "/api/v1/auth/login", "api/v1/auth/verify-email", "api/v1/auth/token", "/v3/api-docs",
                "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
                "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/v1/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/organizations").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/v1/organizations").permitAll()
            .anyRequest().authenticated()
        )
//        .formLogin(form -> form.loginProcessingUrl("api/v1/auth/login"))
        .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * Provides a password encoder for hashing passwords securely.
   *
   * @return a {@link BCryptPasswordEncoder} instance.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
