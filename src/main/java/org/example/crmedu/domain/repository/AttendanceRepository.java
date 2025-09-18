package org.example.crmedu.domain.repository;

import org.example.crmedu.domain.model.AttendanceStatus;

public interface AttendanceRepository extends BaseCrudRepository<AttendanceStatus>{

  AttendanceStatus findByStudentAndLesson(Long studentId, Long lessonId);
}
