package org.example.crmedu.infrastructure.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Jwt;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.jwt.JwtService;
import org.example.crmedu.domain.service.user.UserService;
import org.example.crmedu.infrastructure.auth.jwt.JwtAuthentication;
import org.example.crmedu.infrastructure.auth.jwt.JwtProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This service manages user login, access token generation, and refresh token handling. It validates credentials, issues JWT tokens, and ensures secure token
 * refreshing.
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private static final String BEARER_HEADER_TYPE = "Bearer";

  private final UserService userService;

  private final Map<String, String> refreshStorage = new HashMap<>();

  private final JwtProvider jwtProvider;

  private final PasswordEncoder passwordEncoder;

  @Override
  public Jwt login(User userRequest) {
    var user = userService.findByEmail(userRequest.getEmail());
    if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("Wrong password");
    }
    var accessToken = jwtProvider.generateAccessToken(user);
    var refreshToken = jwtProvider.generateRefreshToken(user);
    refreshStorage.put(user.getEmail(), refreshToken);
    return new Jwt().setType(BEARER_HEADER_TYPE).setAccessToken(accessToken).setRefreshToken(refreshToken);
  }

  @Override
  public Jwt getAccessToken(String refreshToken) {
    var accessToken = getNewAccessToken(refreshToken);
    return new Jwt().setType(BEARER_HEADER_TYPE).setAccessToken(accessToken).setRefreshToken(refreshToken);
  }

  @Override
  public Jwt refresh(String refreshToken) {
    var user = getUserByClaimsOrThrow(refreshToken);
    var newRefreshToken = getNewRefreshToken(refreshToken);
    var accessToken = getNewAccessToken(refreshToken);
    refreshStorage.put(user.getEmail(), newRefreshToken);
    return new Jwt().setType(BEARER_HEADER_TYPE).setAccessToken(accessToken).setRefreshToken(newRefreshToken);
  }

  @Override
  public User getCurrentUser() {
    return userService.findByEmail(getAuthInfo().getEmail());
  }

  private JwtAuthentication getAuthInfo() {
    return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }

  private String getNewAccessToken(String refreshToken) {
    var user = getUserByClaimsOrThrow(refreshToken);
    return jwtProvider.generateAccessToken(user);
  }

  private User getUserByClaimsOrThrow(String refreshToken) {
    if (!jwtProvider.isRefreshTokenValid(refreshToken)) {
      throw new BadCredentialsException("Invalid refresh token");
    }
    var claims = jwtProvider.getRefreshClaims(refreshToken);
    var email = claims.getSubject();
    var saveRefreshToken = refreshStorage.get(email);
    if (saveRefreshToken == null || !saveRefreshToken.equals(refreshToken)) {
      throw new BadCredentialsException("Invalid refresh token");
    }
    return userService.findByEmail(email);
  }

  private String getNewRefreshToken(String refreshToken) {
    var user = getUserByClaimsOrThrow(refreshToken);
    return jwtProvider.generateRefreshToken(user);
  }
}
