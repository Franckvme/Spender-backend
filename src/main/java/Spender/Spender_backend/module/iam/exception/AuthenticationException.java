package Spender.Spender_backend.module.iam.exception;

import Spender.Spender_backend.shared.exception.base.SpenderBusinessException;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends SpenderBusinessException {

  public AuthenticationException(String message, Throwable cause) {
    super(message, cause, HttpStatus.UNAUTHORIZED);
  }

  public AuthenticationException(String message) {
    super(message, HttpStatus.UNAUTHORIZED);
  }
}
