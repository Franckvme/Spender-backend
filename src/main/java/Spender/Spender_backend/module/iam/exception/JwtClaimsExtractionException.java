package Spender.Spender_backend.module.iam.exception;

public class JwtClaimsExtractionException extends JwtTokenException {

  public JwtClaimsExtractionException(Throwable cause) {
    super("Cannot extract claims from token", cause);
  }
}
