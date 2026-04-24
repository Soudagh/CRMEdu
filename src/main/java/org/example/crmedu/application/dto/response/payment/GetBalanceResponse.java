package org.example.crmedu.application.dto.response.payment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetBalanceResponse {

  private Long studentId;

  private Integer balance;

  private Boolean hasDebt;
}