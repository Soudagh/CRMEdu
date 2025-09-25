package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CheckedTask {

  private Long id;

  private CompletedTask completedTask;

  private Tutor tutor;

  private Boolean isChecked;

  private ZonedDateTime inspectionDate;
}
