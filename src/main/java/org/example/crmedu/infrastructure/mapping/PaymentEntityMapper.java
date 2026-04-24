package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Payment;
import org.example.crmedu.infrastructure.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = StudentEntityMapper.class)
public interface PaymentEntityMapper extends BaseEntityMapper<Payment, PaymentEntity> {

  @Override
  @Mapping(source = "studentEntity", target = "student")
  Payment toDomain(PaymentEntity entity);

  @Override
  @Mapping(source = "student", target = "studentEntity")
  PaymentEntity toEntity(Payment domain);

  List<Payment> toDomain(List<PaymentEntity> entities);
}