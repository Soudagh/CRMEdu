package org.example.crmedu.application.controller;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.request.attendance.MarkAttendanceByQrRequest;
import org.example.crmedu.application.dto.request.attendance.PatchAttendanceStatusRequest;
import org.example.crmedu.domain.service.attendance.AttendanceService;
import org.example.crmedu.domain.service.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/attendances")
@RequiredArgsConstructor
public class AttendanceController {

  private final AttendanceService attendanceService;

  private final JwtService jwtService;

  @PatchMapping("/{id}")
  public ResponseEntity<Void> patchStatusById(@PathVariable Long id, @RequestBody PatchAttendanceStatusRequest request) {
    attendanceService.patchAttendanceStatusById(id, request.getAttendanceStatus());
    return ResponseEntity.ok().build();
  }

  @PostMapping("mark")
  @Secured({"STUDENT"})
  public ResponseEntity<Void> markAttendance(@RequestBody MarkAttendanceByQrRequest request) {
    var user = jwtService.getCurrentUser();
    attendanceService.markAttendanceByQrCode(user, request.getQrPayload());
    return ResponseEntity.ok().build();
  }
}
