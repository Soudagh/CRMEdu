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
import javax.crypto.SecretKey;
import lombok.NonNull;
import org.example.crmedu.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final SecretKey jwtAccessSecretKey;
  private final SecretKey jwtRefreshSecretKey;

  public JwtProvider(
      @Value("${jwt.secret.access}") String jwtAccessSecret,
      @Value("${jwt.secret.refresh}") String jwtRefreshSecret
  ) {
    this.jwtAccessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    this.jwtRefreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
  }

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

  public String generateRefreshToken(@NonNull User user) {
    final var now = LocalDateTime.now();
    final var refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
    final var refreshExpiration = Date.from(refreshExpirationInstant);
    return Jwts.builder()
        .subject(user.getEmail())
        .expiration(refreshExpiration)
        .signWith(jwtRefreshSecretKey)
        .compact();
  }

  public boolean isAccessTokenValid(@NonNull String accessToken) {
    return validateToken(accessToken, jwtAccessSecretKey);
  }

  public boolean isRefreshTokenValid(@NonNull String refreshToken) {
    return validateToken(refreshToken, jwtRefreshSecretKey);
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

  public Claims getAccessClaims(@NonNull String token) {
    return getClaims(token, jwtAccessSecretKey);
  }

  public Claims getRefreshClaims(@NonNull String token) {
    return getClaims(token, jwtRefreshSecretKey);
  }

  private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
    return Jwts.parser()
        .verifyWith(secret)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }
}
