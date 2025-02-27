package org.example.crmedu.application.event;

import lombok.Getter;
import org.example.crmedu.domain.model.User;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

  private final User user;

  public OnRegistrationCompleteEvent(User user) {
    super(user);
    this.user = user;
  }
}
