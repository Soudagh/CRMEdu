package org.example.crmedu.application.dto.request.attendance;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MarkAttendanceByQrRequest {

  private String qrPayload;
}
