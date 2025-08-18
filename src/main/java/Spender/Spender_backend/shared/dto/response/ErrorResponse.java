package Spender.Spender_backend.shared.dto.response;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse extends AbstractApiResponse {

  private int statusCode;
  private String message;
  private List<ErrorDetails> errors;

  public ErrorResponse(int statusCode, String message, List<ErrorDetails> errors) {
    super(StatusEnum.ERROR);
    this.statusCode = statusCode;
    this.message = message;
    this.errors = errors;
  }

  public record ErrorDetails(String field, String message) {
  }
}
