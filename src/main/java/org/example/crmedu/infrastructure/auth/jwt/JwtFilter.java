package org.example.crmedu.infrastructure.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * JWT authentication filter that extracts and validates tokens from incoming requests.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

  private static final String BEARER_PREFIX = "Bearer ";

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private final JwtProvider jwtProvider;

  /**
   * Extracts and validates the JWT token from the request, then sets authentication in the security context.
   *
   * @param request the incoming HTTP request.
   * @param response the outgoing HTTP response.
   * @param filterChain the filter chain to continue processing.
   */
  @Override
  @SneakyThrows
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
    var token = getTokenFromRequest((HttpServletRequest) request);
    if (token != null && jwtProvider.isAccessTokenValid(token)) {
      var claims = jwtProvider.getAccessClaims(token);
      var jwtInfoToken = JwtUtils.generate(claims);
      jwtInfoToken.setAuthenticated(true);
      SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
    }
    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    var bearer = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.contains(bearer, BEARER_PREFIX)) {
      return bearer.substring(7);
    }
    return null;
  }
}
