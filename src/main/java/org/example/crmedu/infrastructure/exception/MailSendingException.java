package org.example.crmedu.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class MailSendingException extends RuntimeException {

  public MailSendingException(String message, Throwable cause) {
    super(message, cause);
  }
}
