package Spender.Spender_backend.shared.security;

import Spender.Spender_backend.shared.exception.base.SpenderTechnicalException;
import org.springframework.http.HttpStatus;

public class UserNotAuthenticatedException extends SpenderTechnicalException {

  public UserNotAuthenticatedException() { super("User not authenticated", HttpStatus.UNAUTHORIZED);}
}
