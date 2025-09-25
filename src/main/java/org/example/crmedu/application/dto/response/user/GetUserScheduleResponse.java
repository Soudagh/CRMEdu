package org.example.crmedu.application.dto.response.user;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.application.dto.response.lesson.GetLessonResponse;

@Data
@Accessors(chain = true)
public class GetUserScheduleResponse {
  private List<GetLessonResponse> lessons;
}
