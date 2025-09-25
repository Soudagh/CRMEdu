package org.example.crmedu.domain.service.attendance;

import org.example.crmedu.domain.enums.AttendanceStatusEnum;
import org.example.crmedu.domain.model.AttendanceStatus;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.BaseCrudService;

public interface AttendanceService extends BaseCrudService<AttendanceStatus> {

  void patchAttendanceStatusById(Long id, AttendanceStatusEnum attendanceStatus);

  void markAttendanceByQrCode(User user, String qrPayload);
}
