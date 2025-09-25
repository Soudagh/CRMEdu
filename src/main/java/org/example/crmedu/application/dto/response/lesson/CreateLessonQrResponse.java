package org.example.crmedu.application.dto.response.lesson;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateLessonQrResponse {

  private String qrPayload;

  private ZonedDateTime expiresAt;
}
