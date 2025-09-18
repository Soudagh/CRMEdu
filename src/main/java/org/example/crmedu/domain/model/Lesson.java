package org.example.crmedu.domain.model;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.LessonStatus;

@Data
@Accessors(chain = true)
public class Lesson {

  private Long id;

  private SubjectProgram subjectProgram;

  private Tutor tutor;

  private LessonStatus lessonStatus = LessonStatus.PENDING;

  private LocalDate date;

  private ZonedDateTime startTime;

  private ZonedDateTime endTime;

  private String link;

  private String notes;

  private String color;

  private List<AttendanceStatus> attendances = new ArrayList<>();
}
