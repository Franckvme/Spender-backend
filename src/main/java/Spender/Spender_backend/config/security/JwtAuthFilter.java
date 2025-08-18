package Spender.Spender_backend.config.security;

import Spender.Spender_backend.module.iam.exception.AuthenticationException;
import Spender.Spender_backend.module.iam.security.impl.JwtTokenProvider;
import Spender.Spender_backend.shared.dto.AuthenticatedUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final String BEARER_HEADER = "Bearer ";

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(BEARER_HEADER)) {
      filterChain.doFilter(request, response);
      return;
    }
    try {
      authenticateUser(request, authHeader);
    } catch (Exception e) {
      throw new AuthenticationException("Cannot authenticate user from jwt token", e);
    }
    filterChain.doFilter(request, response);
  }

  private void authenticateUser(HttpServletRequest request, String authHeader) {
    var token = authHeader.replace(BEARER_HEADER, "");
    var username = jwtTokenProvider.extractUsername(token);
    var userId = jwtTokenProvider.extractUserId(token);
    var authorities = jwtTokenProvider.extractAuthorities(token);

    var authentication = UsernamePasswordAuthenticationToken.authenticated(
      new AuthenticatedUser(userId, username),
      null,
      authorities.stream().map(SimpleGrantedAuthority::new).toList()
    );

    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
