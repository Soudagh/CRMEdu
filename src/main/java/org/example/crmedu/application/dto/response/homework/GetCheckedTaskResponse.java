package org.example.crmedu.application.dto.response.homework;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetCheckedTaskResponse {

  private Long id;

  private Long completedTaskId;

  private Long tutorId;

  private String tutorName;

  private Boolean isChecked;

  private String comments;

  private ZonedDateTime inspectionDate;
}