package org.example.crmedu.application.dto.request.attendance;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.AttendanceStatusEnum;

@Data
@Accessors(chain = true)
public class PatchAttendanceStatusRequest {

  private AttendanceStatusEnum attendanceStatus;
}
