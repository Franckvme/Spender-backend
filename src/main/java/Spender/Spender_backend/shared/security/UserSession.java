package Spender.Spender_backend.shared.security;

import Spender.Spender_backend.shared.dto.AuthenticatedUser;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@UtilityClass
public class UserSession {

  private static final Logger logger = LoggerFactory.getLogger(UserSession.class);
  private static AuthenticatedUser authenticatedUserOrThrow() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser user)) {
      throw new UserNotAuthenticatedException();
    }

    logConnectedUser(user);

    return user;
  }

  public static Optional<AuthenticatedUser> authenticatedUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser user)) {
      return Optional.empty();
    }

    logConnectedUser(user);

    return Optional.of(user);
  }

  private static void logConnectedUser(AuthenticatedUser user) {
    logger.info("Connected user: {}", user.username());
  }

}
