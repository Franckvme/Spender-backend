package Spender.Spender_backend.shared.dto.response;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class SingleResponse<T> extends AbstractApiResponse {

  private T data;

  public SingleResponse(T data) {
    super(StatusEnum.SUCCESS);
    this.data = data;
  }
}
