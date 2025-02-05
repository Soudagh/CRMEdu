package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.infrastructure.entity.SubjectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectEntityMapper {

  Subject toSubject(SubjectEntity subjectEntity);

  SubjectEntity toSubjectEntity(Subject subject);
}
