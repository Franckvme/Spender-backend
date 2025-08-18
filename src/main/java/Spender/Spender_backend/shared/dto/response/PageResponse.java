package Spender.Spender_backend.shared.dto.response;

import Spender.Spender_backend.shared.dto.SortOrder;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class PageResponse<T> extends AbstractApiResponse {

  private List<T> data;
  private PageMetadata pageMetadata;
  private PageLinks links;

  public PageResponse(Page<T> page, PageContext pageContext) {
    super(StatusEnum.SUCCESS);
    this.data = page.getContent();
    this.pageMetadata = buildPageMetadata(page);
    this.links = buildPageLinks(page, pageContext);
  }

  private PageLinks buildPageLinks(Page<T> page, PageContext pageContext) {
    var totalPages = page.getTotalPages();
    var firstPageNumber = 0;
    var lastPageNumber = totalPages > 0 ? totalPages - 1 : firstPageNumber;

    return new PageLinks(
      buildUri(page.getPageable(), pageContext),
      buildUri(PageRequest.of(firstPageNumber, page.getSize(), page.getSort()), pageContext),
      buildUri(PageRequest.of(lastPageNumber, page.getSize(), page.getSort()), pageContext),
      page.hasNext() ? buildUri(page.nextPageable(), pageContext) : null,
      page.hasPrevious() ? buildUri(page.previousPageable(), pageContext) : null
    );
  }

  private PageMetadata buildPageMetadata(Page<T> page) {
    return new PageMetadata(
      page.getNumber() + 1,
      page.getSize(),
      page.getTotalElements(),
      page.getTotalPages(),
      page.isLast()
    );
  }

  public record PageMetadata(
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean last
  ) {
  }

  public record PageLinks(
    String self,
    String first,
    String last,
    String next,
    String previous
  ) {
  }

  public record PageContext(
    String baseUrl,
    String path,
    String sortBy,
    SortOrder sortOrder,
    Map<String, Object> params
  ) {
  }

  private static String buildUri(Pageable pageable, PageContext pageContext) {
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(pageContext.baseUrl())
      .path(pageContext.path())
      .queryParam("page", pageable.getPageNumber() + 1)
      .queryParam("size", pageable.getPageSize())
      .queryParam("sortBy", pageContext.sortBy())
      .queryParam("sortOrder", pageContext.sortOrder());

    pageContext.params().forEach(
      (key, value) -> uriComponentsBuilder.queryParamIfPresent(key, Optional.ofNullable(value))
    );

    return uriComponentsBuilder
      .build()
      .toUriString();
  }
}
