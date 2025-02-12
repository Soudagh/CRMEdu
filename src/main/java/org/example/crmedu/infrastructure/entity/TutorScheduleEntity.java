package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.OffsetTime;
import lombok.Getter;
import lombok.Setter;
import org.example.crmedu.domain.model.DaysOfWeek;

@Getter
@Setter
@Embeddable
public class TutorScheduleEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "day_of_week")
  private DaysOfWeek dayOfWeek;

  @Column(name = "time_start")
  private OffsetTime timeStart = OffsetTime.MIN;

  @Column(name = "time_end")
  private OffsetTime timeEnd = OffsetTime.MAX;
}
