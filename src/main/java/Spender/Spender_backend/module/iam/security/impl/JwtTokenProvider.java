package Spender.Spender_backend.module.iam.security.impl;

import Spender.Spender_backend.module.iam.exception.CannotGenerateTokenException;
import Spender.Spender_backend.module.iam.exception.JwtClaimsExtractionException;
import Spender.Spender_backend.module.iam.security.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider implements TokenProvider {

  @Value("${application.security.jwt.secret}")
  private  String secret;
  @Value("${application.security.jwt.token-duration}")
  private  Duration tokenDuration;

  private final Clock clock;



  @Override
  public String generateToken(UUID userId, String username, Set<String> authorities) {
    var currentDate = ZonedDateTime.now(clock);

    try {
      return Jwts
        .builder()
        .subject(username)
        .claim("id", userId)
        .claim("username", username)
        .claim("authorities", authorities)
        .issuedAt(Date.from(currentDate.toInstant()))
        .expiration(Date.from(currentDate.plus(tokenDuration).toInstant()))
        .signWith(buildSecretKey(secret), Jwts.SIG.HS512)
        .compact();
    } catch (RuntimeException e) {
      throw new CannotGenerateTokenException(e);
    }
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public UUID extractUserId(String token) {
    var id = extractClaim(token, (claims) -> claims.get("id", String.class));
    return UUID.fromString(id);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<String> extractAuthorities(String token) {
    return extractClaim(token, (claims) -> claims.get("authorities", List.class));
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    var claims = extractClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractClaims(String token) {
    try {
      return Jwts.parser()
        .verifyWith(buildSecretKey(secret))
        .build()
        .parseSignedClaims(token)
        .getPayload();
    } catch (RuntimeException e) {
      throw new JwtClaimsExtractionException(e);
    }
  }

  private static SecretKey buildSecretKey(String secret) {
    var keyBytes = Base64.getDecoder().decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
