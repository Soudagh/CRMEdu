package org.example.crmedu.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyPairGenerator;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.SneakyThrows;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.infrastructure.auth.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtProviderTest extends BaseUnitTest {

  private JwtProvider jwtProvider;

  private final String accessSecret = Base64.getEncoder().encodeToString(
      "super-secure-access-key-with-256-bit-length-abcdefgh".getBytes()
  );
  private final String refreshSecret = Base64.getEncoder().encodeToString(
      "super-secure-refresh-key-with-256-bit-length-ijklmnop".getBytes()
  );


  @BeforeEach
  void setUp() {
    jwtProvider = new JwtProvider(accessSecret, refreshSecret);
  }

  @Test
  void generateAccessToken_shouldThrowException_ifPassNull() {
    assertThrows(NullPointerException.class, () -> jwtProvider.generateAccessToken(null));
  }

  @Test
  void generateRefreshToken_shouldThrowException_ifPassNull() {
    assertThrows(NullPointerException.class, () -> jwtProvider.generateRefreshToken(null));
  }

  @Test
  void isAccessTokenValid_shouldThrowException_ifPassNull() {
    assertThrows(NullPointerException.class, () -> jwtProvider.isAccessTokenValid(null));
  }

  @Test
  void isRefreshTokenValid_shouldThrowException_ifPassNull() {
    assertThrows(NullPointerException.class, () -> jwtProvider.isRefreshTokenValid(null));
  }

  @Test
  void isAccessTokenValid_shouldReturnFalseForInvalidToken() {
    assertThrows(JwtException.class, () -> jwtProvider.isAccessTokenValid("invalid.token"));
  }

  @Test
  void validateToken_shouldReturnTrueForValidAccessToken() {
    var token = Jwts.builder()
        .subject("user@mail.com")
        .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(accessSecret)))
        .compact();
    assertTrue(jwtProvider.isAccessTokenValid(token));
  }

  @Test
  void validateToken_shouldThrowExceptionForExpiredToken() {
    var expiredToken = Jwts.builder()
        .subject("user@mail.com")
        .expiration(new java.util.Date(System.currentTimeMillis() - 1000))
        .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(accessSecret)))
        .compact();
    assertThrows(JwtException.class, () -> jwtProvider.isAccessTokenValid(expiredToken));
  }

  @Test
  void validateToken_shouldThrowExceptionForInvalidToken() {
    var invalidToken = "invalid.token.here";
    assertThrows(JwtException.class, () -> jwtProvider.isAccessTokenValid(invalidToken));
  }

  @Test
  void validateToken_shouldThrowExceptionForMalformedToken() {
    var malformedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQG1haWwuY29tIn0.";
    assertThrows(MalformedJwtException.class, () -> jwtProvider.isAccessTokenValid(malformedToken));
  }

  @Test
  @SneakyThrows
  void validateToken_shouldThrowExceptionForUnsupportedToken() {
    var keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
    var privateKey = keyPair.getPrivate();
    String unsupportedToken = Jwts.builder()
        .subject("user@mail.com")
        .signWith(privateKey, SignatureAlgorithm.RS256)
        .compact();
    assertThrows(UnsupportedJwtException.class, () -> jwtProvider.isAccessTokenValid(unsupportedToken));
  }

  @Test
  void validateToken_shouldThrowJwtExceptionForCompletelyInvalidToken() {
    var completelyInvalidToken = "this_is_not_a_jwt_token";
    assertThrows(JwtException.class, () -> jwtProvider.isAccessTokenValid(completelyInvalidToken));
  }

  @Test
  void validateToken_shouldThrowJwtExceptionForEmptyToken() {
    assertThrows(JwtException.class, () -> jwtProvider.isAccessTokenValid(""));
  }

  @Test
  void validateToken_shouldThrowJwtExceptionForRandomString() {
    assertThrows(JwtException.class, () -> jwtProvider.isAccessTokenValid("random_string"));
  }

  @Test
  void validateToken_shouldThrowNullPointerExceptionIfTokenIsNull() {
    var secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    var exception = assertThrows(InvocationTargetException.class, () -> invokeValidateToken(null, secretKey));
    assertInstanceOf(NullPointerException.class, exception.getCause());
  }

  @Test
  void validateToken_shouldThrowNullPointerExceptionIfSecretKeyIsNull() {
    var token = Jwts.builder().subject("user@mail.com").signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256)).compact();
    var exception = assertThrows(InvocationTargetException.class, () -> invokeValidateToken(token, null));
    assertInstanceOf(NullPointerException.class, exception.getCause());
  }

  @Test
  void isRefreshClaims_shouldThrowException_ifPassNull() {
    assertThrows(NullPointerException.class, () -> jwtProvider.getRefreshClaims(null));
  }

  @Test
  void getAccessClaims_shouldThrowException_ifPassNull() {
    assertThrows(NullPointerException.class, () -> jwtProvider.getAccessClaims(null));
  }

  @Test
  void getAccessClaims_shouldReturnClaimsForValidToken() {
    var user = mock(User.class);
    when(user.getEmail()).thenReturn("user@mail.com");
    when(user.getRole()).thenReturn(Role.SUPERUSER);
    var accessToken = jwtProvider.generateAccessToken(user);
    assertNotNull(accessToken);
    var claims = jwtProvider.getAccessClaims(accessToken);
    assertNotNull(claims);
    assertEquals("user@mail.com", claims.getSubject());
    assertEquals(Role.SUPERUSER.name(), claims.get("role"));
  }

  @Test
  void getClaims_shouldThrowNullPointerExceptionIfTokenIsNull() {
    var exception = assertThrows(InvocationTargetException.class, () -> invokeGetClaims(null, null));
    assertInstanceOf(NullPointerException.class, exception.getCause());
  }

  @Test
  void getClaims_shouldThrowNullPointerExceptionIfSecretKeyIsNull() {
    var token = Jwts.builder().subject("user@mail.com").signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256)).compact();
    var exception = assertThrows(InvocationTargetException.class, () -> invokeGetClaims(token, null));
    assertInstanceOf(NullPointerException.class, exception.getCause());
  }

  @Test
  void getRefreshClaims_shouldReturnClaimsForValidToken() {
    var user = mock(User.class);
    when(user.getEmail()).thenReturn("user@mail.com");
    var refreshToken = jwtProvider.generateRefreshToken(user);
    assertNotNull(refreshToken);
    var claims = jwtProvider.getRefreshClaims(refreshToken);
    assertNotNull(claims);
    assertEquals("user@mail.com", claims.getSubject());
    assertNotNull(claims.get("rnd"));
  }

  @SneakyThrows
  private boolean invokeValidateToken(String token, SecretKey secret) {
    var method = JwtProvider.class.getDeclaredMethod("validateToken", String.class, SecretKey.class);
    method.setAccessible(true);
    return (boolean) method.invoke(jwtProvider, token, secret);
  }

  @SneakyThrows
  private Claims invokeGetClaims(String token, SecretKey secret) {
    var method = JwtProvider.class.getDeclaredMethod("getClaims", String.class, SecretKey.class);
    method.setAccessible(true);
    return (Claims) method.invoke(jwtProvider, token, secret);
  }
}
