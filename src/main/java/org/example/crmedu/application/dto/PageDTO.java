package org.example.crmedu.application.dto;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageDTO<T> {

  private List<T> content;

  private int page;

  private int limit;

  private long totalCount;

  private int totalPages;
}
