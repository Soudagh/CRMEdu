package org.example.crmedu.application.dto.response.payment;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.PaymentStatus;
import org.example.crmedu.domain.enums.PaymentType;

@Data
@Accessors(chain = true)
public class GetPaymentResponse {

  private Long id;

  private Integer amount;

  private String description;

  private PaymentType paymentType;

  private ZonedDateTime paymentDate;

  private PaymentStatus status;
}