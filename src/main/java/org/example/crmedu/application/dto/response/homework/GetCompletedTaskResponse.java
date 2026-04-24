package org.example.crmedu.application.dto.response.homework;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.CompletedTaskStatus;

@Data
@Accessors(chain = true)
public class GetCompletedTaskResponse {

  private Long id;

  private Long homeworkId;

  private Long studentId;

  private String studentName;

  private String title;

  private String studentAnswer;

  private CompletedTaskStatus status;

  private ZonedDateTime dueDate;

  private List<String> attachmentUrls;
}