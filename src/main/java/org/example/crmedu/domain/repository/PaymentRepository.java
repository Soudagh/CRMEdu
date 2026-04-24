package org.example.crmedu.domain.repository;

import java.util.List;
import org.example.crmedu.domain.model.Payment;

public interface PaymentRepository extends BaseCrudRepository<Payment> {

  List<Payment> findByStudentId(Long studentId);
}