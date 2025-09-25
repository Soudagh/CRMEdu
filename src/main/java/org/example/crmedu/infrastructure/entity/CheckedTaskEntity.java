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
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "checked_task", schema = "crmedu")
public class CheckedTaskEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tutor_id")
  private TutorEntity tutorEntity;

  @Column(name = "comments")
  private String comments;

  @Column(name = "is_checked", nullable = false)
  private Boolean isChecked = false;

  @UpdateTimestamp
  @Column(name = "inspection_date", nullable = false)
  private ZonedDateTime inspectionDate;
}
