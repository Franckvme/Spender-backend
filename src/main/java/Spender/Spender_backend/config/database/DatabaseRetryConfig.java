package Spender.Spender_backend.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableRetry
@Configuration
public class DatabaseRetryConfig {

  @Value("${application.database.retry.back-off-min-duration:500ms}")
  private Duration backOffMinDuration;
  @Value("${application.database.retry.back-off-max-duration:5000ms}")
  private Duration backOffMaxDuration;
  @Value("${application.database.retry.back-off-multiplier:2}")
  private double backOffMultiplier;
  @Value("${application.database.retry.max-attempts:3}")
  private int maxAttempts;

  @Bean
  public RetryOperationsInterceptor databaseRetryInterceptor() {
    return RetryInterceptorBuilder.stateless()
      .retryPolicy(retryPolicy())
      .backOffPolicy(backOffPolicy())
      .recoverer(new DatabaseRetryRecoverer())
      .build();
  }

  private SimpleRetryPolicy retryPolicy() {
    Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
    retryableExceptions.put(TransientDataAccessException.class, true);
    retryableExceptions.put(RecoverableDataAccessException.class, true);

    return new SimpleRetryPolicy(maxAttempts, retryableExceptions);
  }

  private BackOffPolicy backOffPolicy() {
    var exponentialBackoffPolicy = new ExponentialBackOffPolicy();
    exponentialBackoffPolicy.setInitialInterval(backOffMinDuration.toMillis());
    exponentialBackoffPolicy.setMaxInterval(backOffMaxDuration.toMillis());
    exponentialBackoffPolicy.setMultiplier(backOffMultiplier);

    return exponentialBackoffPolicy;
  }

}
