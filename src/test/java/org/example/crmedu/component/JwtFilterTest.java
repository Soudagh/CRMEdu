package org.example.crmedu.component;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.infrastructure.auth.jwt.JwtAuthentication;
import org.example.crmedu.infrastructure.auth.jwt.JwtFilter;
import org.example.crmedu.infrastructure.auth.jwt.JwtProvider;
import org.example.crmedu.infrastructure.auth.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class JwtFilterTest extends BaseUnitTest {

  @InjectMocks
  private JwtFilter jwtFilter;

  @Mock
  private JwtProvider jwtProvider;

  private HttpServletRequest request;

  private ServletResponse response;

  private FilterChain filterChain;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = mock(ServletResponse.class);
    filterChain = mock(FilterChain.class);
    SecurityContextHolder.clearContext();
  }

  @Test
  @SneakyThrows
  void doFilter_shouldAuthenticateUser_whenTokenIsValid() {
    var token = "valid_token";
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtProvider.isAccessTokenValid(token)).thenReturn(true);
    var claims = mock(Claims.class);
    when(jwtProvider.getAccessClaims(token)).thenReturn(claims);
    JwtAuthentication jwtInfoToken = spy(new JwtAuthentication());
    doNothing().when(jwtInfoToken).setAuthenticated(true);
    try (MockedStatic<JwtUtils> jwtUtilsMockedStatic = mockStatic(JwtUtils.class)) {
      jwtUtilsMockedStatic.when(() -> JwtUtils.generate(claims)).thenReturn(jwtInfoToken);
      jwtFilter.doFilter(request, response, filterChain);
      verify(jwtInfoToken, times(1)).setAuthenticated(true);
      assert (SecurityContextHolder.getContext().getAuthentication() == jwtInfoToken);
    }
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  @SneakyThrows
  void doFilter_shouldThrowException_ifTokenIsNull() {
    var token = "invalid_token";
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    jwtFilter.doFilter(request, response, filterChain);
    assert (SecurityContextHolder.getContext().getAuthentication() == null);
    verify(filterChain, times(1)).doFilter(request, response);
  }
}
