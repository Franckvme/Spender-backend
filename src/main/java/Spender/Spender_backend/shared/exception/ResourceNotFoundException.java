package Spender.Spender_backend.shared.exception;

import Spender.Spender_backend.shared.exception.base.SpenderBusinessException;
import lombok.Getter;


public class ResourceNotFoundException extends SpenderBusinessException {

  @Getter
  private final String field;
  @Getter
  private final String value;

  public ResourceNotFoundException(String field, String value) {
    super("Entry of [%s] with value [%s] not found".formatted(field, value), org.springframework.http.HttpStatus.NOT_FOUND);
    this.field = field;
    this.value = value;
  }
}
