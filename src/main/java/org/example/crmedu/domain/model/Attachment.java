package org.example.crmedu.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Attachment {

  private Long id;

  private CompletedTask completedTask;

  private String url;
}
