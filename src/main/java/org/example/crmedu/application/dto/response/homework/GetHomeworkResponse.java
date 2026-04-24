package org.example.crmedu.application.dto.response.homework;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.HomeworkStatus;

@Data
@Accessors(chain = true)
public class GetHomeworkResponse {

  private Long id;

  private Long lessonId;

  private String title;

  private String description;

  private ZonedDateTime startDate;

  private ZonedDateTime endDate;

  private HomeworkStatus status;
}