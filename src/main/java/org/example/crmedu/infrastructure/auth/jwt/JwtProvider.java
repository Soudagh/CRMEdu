package org.example.crmedu.infrastructure.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.NonNull;
import org.example.crmedu.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class manages access and refresh tokens, ensuring secure authentication. It generates signed JWTs for users, validates incoming tokens, and extracts
 * claims.
 */
@Component
public class JwtProvider {

  private final SecretKey jwtAccessSecretKey;
  private final SecretKey jwtRefreshSecretKey;

  /**
   * Initializes secret keys for JWT signing.
   *
   * @param jwtAccessSecret the secret key for access tokens
   * @param jwtRefreshSecret the secret key for refresh tokens
   */
  public JwtProvider(
      @Value("${jwt.secret.access}") String jwtAccessSecret,
      @Value("${jwt.secret.refresh}") String jwtRefreshSecret
  ) {
    this.jwtAccessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    this.jwtRefreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
  }

  /**
   * Generates an access token for a user.
   *
   * @param user the authenticated user
   * @return the signed JWT access token
   */
  public String generateAccessToken(@NonNull User user) {
    final var now = LocalDateTime.now();
    final var accessExpirationInstant = now.plusHours(5).atZone(ZoneId.systemDefault()).toInstant();
    final var accessExpiration = Date.from(accessExpirationInstant);
    return Jwts.builder()
        .subject(user.getEmail())
        .expiration(accessExpiration)
        .signWith(jwtAccessSecretKey)
        .claim("role", user.getRole())
        .compact();
  }

  /**
   * Generates a refresh token for a user.
   *
   * @param user the authenticated user
   * @return the signed JWT refresh token
   */
  public String generateRefreshToken(@NonNull User user) {
    final var now = LocalDateTime.now();
    final var refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
    final var refreshExpiration = Date.from(refreshExpirationInstant);
    return Jwts.builder()
        .subject(user.getEmail())
        .expiration(refreshExpiration)
        .claim("rnd", UUID.randomUUID().toString())
        .signWith(jwtRefreshSecretKey)
        .compact();
  }

  /**
   * Validates the given access token.
   *
   * @param accessToken the token to validate
   * @return true if valid, otherwise throws an exception
   */
  public boolean isAccessTokenValid(@NonNull String accessToken) {
    return validateToken(accessToken, jwtAccessSecretKey);
  }

  /**
   * Validates the given refresh token.
   *
   * @param refreshToken the token to validate
   * @return true if valid, otherwise throws an exception
   */
  public boolean isRefreshTokenValid(@NonNull String refreshToken) {
    return validateToken(refreshToken, jwtRefreshSecretKey);
  }

  /**
   * Extracts claims from an access token.
   *
   * @param token the JWT token
   * @return the extracted claims
   */
  public Claims getAccessClaims(@NonNull String token) {
    return getClaims(token, jwtAccessSecretKey);
  }

  /**
   * Extracts claims from a refresh token.
   *
   * @param token the JWT token
   * @return the extracted claims
   */
  public Claims getRefreshClaims(@NonNull String token) {
    return getClaims(token, jwtRefreshSecretKey);
  }

  private boolean validateToken(@NonNull String token, @NonNull SecretKey secret) {
    try {
      Jwts.parser()
          .verifyWith(secret)
          .build()
          .parseSignedClaims(token);
      return true;
    } catch (ExpiredJwtException expEx) {
      throw new JwtException("Token expired");
    } catch (UnsupportedJwtException unsEx) {
      throw new UnsupportedJwtException("Unsupported JWT token");
    } catch (MalformedJwtException mjEx) {
      throw new MalformedJwtException("Malformed JWT token");
    } catch (Exception e) {
      throw new JwtException("Invalid JWT token");
    }
  }

  private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
    return Jwts.parser()
        .verifyWith(secret)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
