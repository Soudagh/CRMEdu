package org.example.crmedu.domain.service.payment;

import java.util.List;
import org.example.crmedu.domain.model.Payment;
import org.example.crmedu.domain.model.Student;

public interface PaymentService {

  Student getStudentWithBalance(Long userId);

  List<Payment> getPaymentHistory(Long userId);

  Integer deposit(Long userId, Integer amount, String description);
}