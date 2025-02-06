package org.example.crmedu.application.dto.error;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Represents an error response containing the message and stack trace of an exception. This class is used to standardize error handling across the
 * application.
 */
@Data
@Accessors(chain = true)
public class SystemError {

  /**
   * A human-readable error message describing the exception.
   */
  private String message;

  /**
   * The stack trace of the exception as a string.
   */
  private String stackTrace;
}