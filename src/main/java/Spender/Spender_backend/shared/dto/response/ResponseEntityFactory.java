package Spender.Spender_backend.shared.dto.response;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityFactory {

  public static ResponseEntity<Void> buildEmptyResponse(HttpStatus status) {
    return ResponseEntity.status(status).build();
  }

  public static <T> ResponseEntity<SingleResponse<T>> buildSingleResponse(HttpStatus status, T t) {
    return ResponseEntity.status(status).body(new SingleResponse<>(t));
  }

  public static <T> ResponseEntity<ListResponse<T>> buildListResponse(HttpStatus status, List<T> t) {
    return ResponseEntity.status(status).body(new ListResponse<>(t));
  }

  public static <T> ResponseEntity<PageResponse<T>> buildPageResponse(HttpStatus status, PageContextWrapper<T> pageContextWrapper) {
    return ResponseEntity.status(status).body(new PageResponse<>(pageContextWrapper.page(), pageContextWrapper.pageContext()));
  }

  public record PageContextWrapper<T>(Page<T> page, PageResponse.PageContext pageContext) {
  }
}
