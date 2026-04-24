package org.example.crmedu.infrastructure.repository.payment;

import java.util.List;
import org.example.crmedu.domain.model.Payment;
import org.example.crmedu.domain.repository.PaymentRepository;
import org.example.crmedu.infrastructure.entity.PaymentEntity;
import org.example.crmedu.infrastructure.mapping.PaymentEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryImpl extends BaseRepository<Payment, PaymentEntity, Long>
    implements PaymentRepository {

  private final DataPaymentRepository paymentRepository;
  private final PaymentEntityMapper mapper;

  public PaymentRepositoryImpl(DataPaymentRepository paymentRepository, PaymentEntityMapper mapper) {
    super(paymentRepository, mapper);
    this.paymentRepository = paymentRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Payment> findByStudentId(Long studentId) {
    return mapper.toDomain(paymentRepository.findAllByStudentEntity_IdOrderByPaymentDateDesc(studentId));
  }
}