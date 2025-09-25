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
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.SubscriptionStatus;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "subscription", schema = "crmedu")
public class SubscriptionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program_id")
  private ProgramEntity program;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private StudentEntity student;

  @Column(name = "remaining_lessons", nullable = false)
  private Integer remainingLessons;

  @CreationTimestamp
  @Column(name = "assignment_date", nullable = false)
  private ZonedDateTime assignmentDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "subscription_status", nullable = false)
  private SubscriptionStatus subscriptionStatus = SubscriptionStatus.ACTIVE;
}
