package org.example.crmedu.application.mapping;

import java.util.List;
import org.example.crmedu.application.dto.response.payment.GetPaymentResponse;
import org.example.crmedu.domain.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentDTOMapper {

  GetPaymentResponse paymentToGetResponse(Payment payment);

  List<GetPaymentResponse> paymentsToGetResponses(List<Payment> payments);
}