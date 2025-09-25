package org.example.crmedu.application.dto.request.lesson;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PatchLessonNotesRequest {

  private String notes;
}
