package org.example.crmedu.infrastructure.mapping;

import org.example.crmedu.domain.model.AttendanceStatus;
import org.example.crmedu.infrastructure.entity.AttendanceStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LessonEntityMapper.class})
public interface AttendanceStatusMapper extends BaseEntityMapper<AttendanceStatus, AttendanceStatusEntity> {

}
