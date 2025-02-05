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

  private List<T> content;

  private int page;

  private int limit;

  private long totalCount;

  private int totalPages;
}
