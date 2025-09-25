package org.example.crmedu.infrastructure.repository.attendance;

import org.example.crmedu.infrastructure.entity.AttendanceStatusEntity;
import org.example.crmedu.infrastructure.repository.BaseDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataAttendanceStatusRepository extends BaseDataRepository<AttendanceStatusEntity, Long> {

  AttendanceStatusEntity findAttendanceStatusEntityByStudent_IdAndLesson_Id(Long studentId, Long lessonId);
}
