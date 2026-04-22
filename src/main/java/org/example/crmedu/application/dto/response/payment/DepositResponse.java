package org.example.crmedu.application.dto.response.payment;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A DTO representing the response after a successful deposit operation.
 */
@Data
@Accessors(chain = true)
public class DepositResponse {

  private Integer balance;
}
