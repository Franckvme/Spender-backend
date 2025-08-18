package Spender.Spender_backend.shared.exception;

import Spender.Spender_backend.shared.exception.base.SpenderBusinessException;
import lombok.Getter;

@Getter
public class DuplicateResourceException extends SpenderBusinessException {

  private final String field;
  private final String value;

  public DuplicateResourceException(String field, String value) {
    super("Duplicate entry for [%s] with value [%s]".formatted(field, value),
      org.springframework.http.HttpStatus.CONFLICT);
    this.field = field;
    this.value = value;
  }
}
