package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.CompletedTaskStatus;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "completed_task", schema = "crmedu")
public class CompletedTaskEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_id")
  private TaskEntity taskEntity;

  @Column(name = "student_answer", nullable = false)
  private String studentAnswer;

  @Column(name = "status", nullable = false)
  private CompletedTaskStatus completedTaskStatus = CompletedTaskStatus.NO_ANSWER;
}
