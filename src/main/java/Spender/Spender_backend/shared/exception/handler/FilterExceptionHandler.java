package Spender.Spender_backend.shared.exception.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class FilterExceptionHandler extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(FilterExceptionHandler.class);

  private final HandlerExceptionResolver exceptionResolver;

  public FilterExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
    this.exceptionResolver = exceptionResolver;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
    try {
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      logger.error("Spring Security Filter Chain Exception:", e);
      exceptionResolver.resolveException(request, response, null, e);
    }
  }
}
