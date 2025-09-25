package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Task {

  private Long id;

  private Lesson lesson;

  private String title;

  private String description;

  private String rightAnswer;

  private ZonedDateTime startDate;

  private ZonedDateTime endDate;
}
