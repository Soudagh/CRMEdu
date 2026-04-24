package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.CompletedTaskStatus;

@Data
@Accessors(chain = true)
public class CompletedTask {

  private Long id;

  private Task task;

  private Student student;

  private String title;

  private String studentAnswer;

  private CompletedTaskStatus completedTaskStatus;

  private ZonedDateTime dueDate;
}
