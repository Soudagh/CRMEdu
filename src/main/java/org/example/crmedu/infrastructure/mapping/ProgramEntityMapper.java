package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Program;
import org.example.crmedu.infrastructure.entity.ProgramEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SubjectProgramEntityMapper.class)
public interface ProgramEntityMapper {

  @Mapping(target = "organization", ignore = true)
  Program toDomain(ProgramEntity entity);
}

