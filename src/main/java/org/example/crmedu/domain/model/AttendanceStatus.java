package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.AttendanceStatusEnum;

@Data
@Accessors(chain = true)
public class AttendanceStatus {

  private Long id;

  private Long lesson;

  private Student student;

  private AttendanceStatusEnum attendanceStatus;
}
