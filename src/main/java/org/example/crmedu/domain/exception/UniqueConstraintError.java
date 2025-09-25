package org.example.crmedu.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UniqueConstraintError {

  String fieldName;

  String fieldValue;
}
