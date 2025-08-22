package Spender.Spender_backend.shared.builder;

import Spender.Spender_backend.shared.dto.SortOrder;
import Spender.Spender_backend.shared.dto.response.PageResponse;
import Spender.Spender_backend.shared.dto.response.ResponseEntityFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PageContextWrapperBuilder {

  @Value("${application.base-url}")
  private String baseUrl;

  public <T> ResponseEntityFactory.PageContextWrapper<T> build(
    HttpServletRequest httpServletRequest,
    String sortBy,
    SortOrder sortOrder,
    Map<String, Object> params,
    Page<T> page
  ) {
    var context = new PageResponse.PageContext(
      baseUrl,
      httpServletRequest.getRequestURI(),
      sortBy,
      sortOrder,
      params
    );

    return new ResponseEntityFactory.PageContextWrapper<>(page, context);
  }
}
