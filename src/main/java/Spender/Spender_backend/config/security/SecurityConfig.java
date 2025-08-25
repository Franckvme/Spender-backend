package Spender.Spender_backend.config.security;


import Spender.Spender_backend.shared.exception.handler.FilterExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

  public static final String[] PUBLIC_URLS = {
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/actuator/**",
    "/mgmt/**",
    "/iam/auth/**",
    "/iam/user/**",
  };

  private final CorsConfigurationSource corsConfigurationSource;
  private final JwtAuthFilter jwtAuthFilter;
  private final FilterExceptionHandler filterExceptionHandler;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
      .authorizeHttpRequests(authorizeHttpRequests ->
        authorizeHttpRequests
          .requestMatchers(PUBLIC_URLS).permitAll()
          .anyRequest().authenticated()
      )
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(filterExceptionHandler, DisableEncodeUrlFilter.class)
      .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new DefaultAuthenticationEntryPoint())
      );

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    // JdbcDaoImpl is not used in our case, but it is required to prevents memory user creation on startup
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(new JdbcDaoImpl());
    daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder);
    return daoAuthenticationProvider;
  }

  private static class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
      throw new Spender.Spender_backend.module.iam.exception.AuthenticationException("No authentication found", authException);
    }
  }

}
