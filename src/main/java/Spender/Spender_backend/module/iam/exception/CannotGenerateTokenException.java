package Spender.Spender_backend.module.iam.exception;

import org.springframework.http.HttpStatus;

public class CannotGenerateTokenException extends JwtTokenException {

  public CannotGenerateTokenException(Throwable cause) {
    super("Cannot generate token", cause, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
