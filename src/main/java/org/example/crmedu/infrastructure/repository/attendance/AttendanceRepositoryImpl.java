package org.example.crmedu.infrastructure.repository.attendance;

import org.example.crmedu.domain.model.AttendanceStatus;
import org.example.crmedu.domain.repository.AttendanceRepository;
import org.example.crmedu.infrastructure.entity.AttendanceStatusEntity;
import org.example.crmedu.infrastructure.mapping.AttendanceStatusMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

@Component
public class AttendanceRepositoryImpl extends BaseRepository<AttendanceStatus, AttendanceStatusEntity, Long> implements AttendanceRepository {

  private final DataAttendanceStatusRepository repository;

  private final AttendanceStatusMapper mapper;

  public AttendanceRepositoryImpl(DataAttendanceStatusRepository attendanceStatusRepository,
      AttendanceStatusMapper mapper) {
    super(attendanceStatusRepository, mapper);
    repository = attendanceStatusRepository;
    this.mapper = mapper;
  }

  @Override
  public AttendanceStatus findByStudentAndLesson(Long studentId, Long lessonId) {
    return mapper.toDomain(repository.findAttendanceStatusEntityByStudent_IdAndLesson_Id(studentId, lessonId));
  }
}
