package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing a link entity that contains information about site name and its link. This class is used as a domain model.
 */
@Data
@Accessors
public class Link {

  /**
   * Name of the site.
   */
  private String socialName;

  /**
   * Link to the site.
   */
  private String link;
}
