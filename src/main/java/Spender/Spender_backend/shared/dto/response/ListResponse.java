package Spender.Spender_backend.shared.dto.response;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class ListResponse<T> extends AbstractApiResponse {

  private List<T> data;

  public ListResponse(List<T> data) {
    super(StatusEnum.SUCCESS);
    this.data = data;
  }
}
