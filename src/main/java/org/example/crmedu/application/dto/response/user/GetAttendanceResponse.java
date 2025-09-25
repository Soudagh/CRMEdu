package org.example.crmedu.application.dto.response.user;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.application.dto.response.student.GetStudentResponse;
import org.example.crmedu.domain.enums.AttendanceStatusEnum;

@Data
@Accessors(chain = true)
public class GetAttendanceResponse {

  private Long id;

  private GetStudentResponse student;

  private AttendanceStatusEnum attendanceStatus;
}
