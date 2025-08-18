package Spender.Spender_backend.shared.exception.database;

import Spender.Spender_backend.shared.exception.base.SpenderTechnicalException;
import org.springframework.http.HttpStatus;

public class DatabaseException extends SpenderTechnicalException {

  public DatabaseException(String message, Throwable cause) {
    super(message, cause, HttpStatus.SERVICE_UNAVAILABLE);
  }
}
