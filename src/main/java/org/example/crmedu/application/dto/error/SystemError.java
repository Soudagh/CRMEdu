package org.example.crmedu.application.dto.error;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SystemError {

  private String message;

  private String stackTrace;
}