package org.example.crmedu.domain.event;

import lombok.Getter;
import org.example.crmedu.domain.model.User;
import org.springframework.context.ApplicationEvent;

/**
 * Event triggered upon successful completion of a user registration process.
 * <p>
 * This event is published when a new user completes the registration process, allowing event listeners to perform additional actions, such as sending a
 * confirmation email or setting up default user preferences.
 * </p>
 * <p>
 * The event carries the registered {@link User} object, which can be accessed by event listeners to retrieve user-related information.
 * </p>
 */

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

  private final User user;

  /**
   * Creates a new {@code OnRegistrationCompleteEvent}.
   *
   * @param user The user who has successfully registered.
   */
  public OnRegistrationCompleteEvent(User user) {
    super(user);
    this.user = user;
  }
}
