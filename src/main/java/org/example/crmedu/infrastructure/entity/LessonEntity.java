package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.LessonStatus;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "lesson", schema = "crmedu")
public class LessonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subject_program_id")
  private SubjectProgramEntity subjectProgram;

  @ManyToOne
  @JoinColumn(name = "tutor_id")
  private TutorEntity tutor;

  @Enumerated(EnumType.STRING)
  @Column(name = "lesson_status", nullable = false)
  private LessonStatus lessonStatus = LessonStatus.PENDING;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "start_time", nullable = false)
  private ZonedDateTime startTime;

  @Column(name = "end_time", nullable = false)
  private ZonedDateTime endTime;

  @Column(name = "link")
  private String link;

  @Column(name = "notes")
  private String notes;

  @Column(name = "color", nullable = false)
  private String color = "#00FF00";

  @OneToMany(mappedBy = "lesson")
  private List<AttendanceStatusEntity> attendances;
}
