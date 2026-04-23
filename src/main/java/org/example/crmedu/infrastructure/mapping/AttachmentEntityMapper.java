package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.Attachment;
import org.example.crmedu.infrastructure.entity.AttachmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CompletedTaskEntityMapper.class)
public interface AttachmentEntityMapper extends BaseEntityMapper<Attachment, AttachmentEntity> {

  @Override
  @Mapping(source = "completedTaskEntity", target = "completedTask")
  Attachment toDomain(AttachmentEntity entity);

  @Override
  @Mapping(source = "completedTask", target = "completedTaskEntity")
  AttachmentEntity toEntity(Attachment domain);

  List<Attachment> toDomain(List<AttachmentEntity> entities);
}