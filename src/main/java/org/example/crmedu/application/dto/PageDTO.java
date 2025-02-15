package org.example.crmedu.application.dto;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a paginated response. Used for returning a subset of elements along with pagination metadata.
 *
 * @param <T> the type of elements contained in the page
 */
@Data
@Accessors(chain = true)
public class PageDTO<T> {

  private List<T> content;

  private int page;

  private int limit;

  private long totalCount;

  private int totalPages;
}
