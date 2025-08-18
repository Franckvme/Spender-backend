package Spender.Spender_backend.module.iam.security;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TokenProvider {

  String generateToken(UUID userId, String username, Set<String> authorities);

  String extractUsername(String token);

  UUID extractUserId(String token);

  List<String> extractAuthorities(String token);
}
