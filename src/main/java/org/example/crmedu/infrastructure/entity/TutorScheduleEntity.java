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
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.DaysOfWeek;

/**
 * JPA-entity representing a tutor schedule.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(
    name = "tutor_free_time",
    schema = "crmedu",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tutor_id", "day_of_week", "time_start", "time_end"})
)
public class TutorScheduleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "day_of_week")
  private DaysOfWeek dayOfWeek;

  @Column(name = "time_start")
  private OffsetTime timeStart = OffsetTime.MIN;

  @Column(name = "time_end")
  private OffsetTime timeEnd = OffsetTime.MAX;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tutor_id")
  private TutorEntity tutor;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TutorScheduleEntity other)) {
      return false;
    }
    return id != null && id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
