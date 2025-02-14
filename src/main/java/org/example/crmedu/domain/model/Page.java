package org.example.crmedu.domain.model;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing a paginated response. Used to return a subset of elements along with pagination metadata.
 *
 * @param <T> the type of elements contained in the page
 */
@Data
@Accessors(chain = true)
public class Page<T> {

  /**
   * The list of elements contained on the current page.
   */
  private List<T> content;

  /**
   * The current page number (starting from 0).
   */
  private int page;

  /**
   * The number of maximum count of elements per page.
   */
  private int limit;

  /**
   * The total number of elements across all pages.
   */
  private long totalCount;

  /**
   * The total number of pages, calculated based on {@code totalCount} and {@code limit}.
   */
  private int totalPages;
}
