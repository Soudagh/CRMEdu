package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LessonQr {

  private String qrPayload;

  private ZonedDateTime expiresAt;
}
