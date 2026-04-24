package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.PaymentStatus;
import org.example.crmedu.domain.enums.PaymentType;

@Data
@Accessors(chain = true)
public class Payment {

  private Long id;

  private Student student;

  private Integer amount;

  private String description;

  private PaymentType paymentType;

  private ZonedDateTime paymentDate;

  private PaymentStatus status;
}