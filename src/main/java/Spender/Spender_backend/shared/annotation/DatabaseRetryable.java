package Spender.Spender_backend.shared.annotation;

import org.springframework.retry.annotation.Retryable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Retryable(interceptor = "databaseRetryInterceptor")
public @interface DatabaseRetryable {
}
