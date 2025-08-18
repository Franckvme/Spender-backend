package Spender.Spender_backend.module.iam.exception;

import Spender.Spender_backend.shared.exception.base.SpenderTechnicalException;
import org.springframework.http.HttpStatus;

public class JwtTokenException extends SpenderTechnicalException {

  public JwtTokenException(String message, Throwable cause, HttpStatus httpStatus) {
    super(message, cause, httpStatus);
  }

  public JwtTokenException(String message, Throwable cause) {
    super(message, cause, HttpStatus.UNAUTHORIZED);
  }
}
