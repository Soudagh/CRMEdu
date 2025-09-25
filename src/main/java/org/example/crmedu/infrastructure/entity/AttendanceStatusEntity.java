package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.AttendanceStatusEnum;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "attendance_status", schema = "crmedu")
public class AttendanceStatusEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private StudentEntity student;

  @ManyToOne
  @JoinColumn(name = "lesson_id")
  private LessonEntity lesson;

  @Column(name = "attendance_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private AttendanceStatusEnum attendanceStatus = AttendanceStatusEnum.NOT_PRESENCE;
}
