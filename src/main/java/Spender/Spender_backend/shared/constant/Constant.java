package Spender.Spender_backend.shared.constant;

import Spender.Spender_backend.shared.dto.SortOrder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

  public static final int DEFAULT_PAGE_SIZE = 50;
  public static final int DEFAULT_PAGE_NUMBER = 1;
  public static final SortOrder DEFAULT_SORT_ORDER = SortOrder.DESC;
  public static final String DEFAULT_SORT_BY = "createdAt";
}
