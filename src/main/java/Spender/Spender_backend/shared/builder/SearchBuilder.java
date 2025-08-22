package Spender.Spender_backend.shared.builder;


import Spender.Spender_backend.shared.dto.SortOrder;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class SearchBuilder {

  public static Sort buildSort(String sortColumn, SortOrder sortOrder) {
    Sort.Direction direction = Sort.Direction.fromString(sortOrder.name());
    return Sort.by(direction, sortColumn);
  }
}
