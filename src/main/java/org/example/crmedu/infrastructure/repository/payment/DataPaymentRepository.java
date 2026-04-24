package org.example.crmedu.infrastructure.repository.payment;

import java.util.List;
import org.example.crmedu.infrastructure.entity.PaymentEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPaymentRepository extends BaseDataRepository<PaymentEntity, Long> {

  List<PaymentEntity> findAllByStudentEntity_IdOrderByPaymentDateDesc(Long studentId);
}