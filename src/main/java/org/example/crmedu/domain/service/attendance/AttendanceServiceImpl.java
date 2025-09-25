package org.example.crmedu.domain.service.attendance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Base64;
import org.example.crmedu.domain.components.QrSigner;
import org.example.crmedu.domain.enums.AttendanceStatusEnum;
import org.example.crmedu.domain.model.AttendanceStatus;
import org.example.crmedu.domain.model.QrPayload;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.AttendanceRepository;
import org.example.crmedu.domain.service.BaseService;
import org.example.crmedu.domain.service.student.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceServiceImpl extends BaseService<AttendanceStatus> implements AttendanceService {

  private final AttendanceRepository attendanceRepository;

  private final StudentService studentService;

  private final ObjectMapper objectMapper;

  private final QrSigner qrSigner;

  public AttendanceServiceImpl(AttendanceRepository attendanceRepository, StudentService studentService, ObjectMapper objectMapper, QrSigner qrSigner) {
    super(attendanceRepository, AttendanceStatus.class);
    this.attendanceRepository = attendanceRepository;
    this.studentService = studentService;
    this.objectMapper = objectMapper;
    this.qrSigner = qrSigner;
  }

  @Override
  @Transactional
  public void patchAttendanceStatusById(Long id, AttendanceStatusEnum attendanceStatus) {
    var attendance = findById(id);
    attendanceRepository.update(attendance.setAttendanceStatus(attendanceStatus));
  }

  @Transactional
  @Override
  public void markAttendanceByQrCode(User user, String qrPayload) {
    var student = studentService.getStudentByUserId(user.getId());

    var decodedBytes = Base64.getDecoder().decode(qrPayload);
    var decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);

    try {
      QrPayload payload = objectMapper.readValue(decodedJson, QrPayload.class);
      if (payload.getExpiredAt().plusMinutes(10).isBefore(ZonedDateTime.now())) {
        throw new RuntimeException("QR expired");
      }
      var attendance = attendanceRepository.findByStudentAndLesson(student.getId(), payload.getId());
      patchAttendanceStatusById(attendance.getId(), AttendanceStatusEnum.PRESENCE);

    } catch (JsonProcessingException e) {
      throw new RuntimeException("Invalid QR payload", e);
    }
  }

  @Override
  public AttendanceStatus create(AttendanceStatus attendanceStatus) {
    return null;
  }

  @Override
  public void update(AttendanceStatus attendanceStatus, Long id) {
  }
}
