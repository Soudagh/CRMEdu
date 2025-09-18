package org.example.crmedu.infrastructure.mapping;

import java.util.List;
import org.example.crmedu.domain.model.SubjectProgram;
import org.example.crmedu.infrastructure.entity.SubjectProgramEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LessonEntityMapper.class, SubjectEntityMapper.class})
public interface SubjectProgramEntityMapper {

  SubjectProgram toDomain(SubjectProgramEntity entity);

  List<SubjectProgram> toDomain(List<SubjectProgramEntity> entities);
}
