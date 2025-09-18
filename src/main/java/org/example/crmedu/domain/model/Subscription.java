package org.example.crmedu.domain.model;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.SubscriptionStatus;

@Data
@Accessors(chain = true)
public class Subscription {

  private Long id;

  private Program program;

  private ZonedDateTime assignmentDate;

  private SubscriptionStatus subscriptionStatus;
}
