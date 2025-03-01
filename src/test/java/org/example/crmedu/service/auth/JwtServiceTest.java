package org.example.crmedu.service.auth;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Claims;
import java.util.Map;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.user.UserService;
import org.example.crmedu.infrastructure.auth.jwt.JwtProvider;
import org.example.crmedu.infrastructure.service.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest extends BaseUnitTest {

  @InjectMocks
  private JwtServiceImpl jwtService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserService userService;

  @Mock
  private JwtProvider jwtProvider;

  @Test
  void login_shouldThrowException_ifWrongPassword() {
    var user = getMockObject(User.class).setPassword("password1");
    var loginRequest = getMockObject(User.class).setPassword("password2");
    when(userService.findByEmail(loginRequest.getEmail())).thenReturn(user);
    when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);
    assertThrows(BadCredentialsException.class, () -> jwtService.login(loginRequest));
  }

  @Test
  void refresh_shouldThrowException_ifInvalidInputRefreshToken() {
    var refreshToken = "a";
    when(jwtProvider.isRefreshTokenValid(refreshToken)).thenReturn(false);
    assertThrows(BadCredentialsException.class, () -> jwtService.refresh(refreshToken));
  }

  @Test
  void refresh_shouldThrowException_ifNullRefreshTokenInStorage() {
    var refreshToken = "a";
    when(jwtProvider.isRefreshTokenValid(refreshToken)).thenReturn(true);
    var claims = mock(Claims.class);
    when(jwtProvider.getRefreshClaims(refreshToken)).thenReturn(claims);
    var email = "mail@mail.ru";
    when(claims.getSubject()).thenReturn(email);
    assertThrows(BadCredentialsException.class, () -> jwtService.refresh(refreshToken));
  }

  @Test
  void refresh_shouldThrowException_ifRefreshTokenDoesNotMatchStoredToken() {
    var refreshToken = "a";
    when(jwtProvider.isRefreshTokenValid(refreshToken)).thenReturn(true);
    var claims = mock(Claims.class);
    when(jwtProvider.getRefreshClaims(refreshToken)).thenReturn(claims);
    var email = "mail@mail.ru";
    when(claims.getSubject()).thenReturn(email);
    setPrivateField(jwtService, "refreshStorage", Map.of(email, "actual_token"));
    assertThrows(BadCredentialsException.class, () -> jwtService.refresh(refreshToken));
  }
}
