package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A class representing a subject entity with a unique identifier and name. This class is used as a domain model.
 */
@Data
@Accessors(chain = true)
public class Subject {

  /**
   * The unique identifier of the subject.
   */
  private Long id;

  /**
   * The name of the subject. Must be unique.
   */
  private String name;
}
