package org.example.crmedu.application.dto.response.lesson;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.application.dto.response.program.GetProgramResponse;
import org.example.crmedu.application.dto.response.subject.GetSubjectResponse;
import org.example.crmedu.application.dto.response.tutor.GetTutorSimpleResponse;
import org.example.crmedu.application.dto.response.user.GetAttendanceResponse;
import org.example.crmedu.domain.enums.LessonStatus;

@Data
@Accessors(chain = true)
public class GetLessonResponse {
  private Long id;

  private GetSubjectResponse subject;

  private GetProgramResponse program;

  private GetTutorSimpleResponse tutor;

  private List<GetAttendanceResponse> attendances;

  private String notes;

  private LessonStatus lessonStatus;

  private ZonedDateTime startTime;

  private ZonedDateTime endTime;

  private LocalDate date;

  private String link;

  private String color;
}
