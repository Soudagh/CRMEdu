package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Subscription;
import org.example.crmedu.infrastructure.entity.SubscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProgramEntityMapper.class)
public interface SubscriptionEntityMapper {

  Subscription toDomain(SubscriptionEntity subscription);

}
