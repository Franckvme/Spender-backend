package Spender.Spender_backend.shared.dto.response;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public abstract class AbstractApiResponse {

  private StatusEnum status;
  private Instant timestamp;

  public AbstractApiResponse(StatusEnum status) {
    this.status = status;
    this.timestamp = Instant.now();
  }

  public enum StatusEnum {
    SUCCESS, ERROR
  }
}
