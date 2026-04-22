package org.example.crmedu.application.dto.request.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing a request to deposit funds into a student's balance.
 */
@Data
@Accessors(chain = true)
public class DepositRequest {

  @NotNull(message = "Amount must not be null")
  @Positive(message = "Amount must be positive")
  private Integer amount;

  private String description;
}
