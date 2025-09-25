package org.example.crmedu.application.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a single validation violation.
 */
@Getter
@RequiredArgsConstructor
public class ViolationDto {

  /**
   * The field that caused the validation error.
   */
  private final String fieldName;

  /**
   * The specific validation message.
   */
  private final String message;
}
