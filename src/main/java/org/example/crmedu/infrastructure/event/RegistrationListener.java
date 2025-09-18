package org.example.crmedu.infrastructure.event;

import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.event.OnRegistrationCompleteEvent;
import org.example.crmedu.domain.service.auth.EmailService;
import org.example.crmedu.domain.service.auth.VerificationTokenService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listens for {@link OnRegistrationCompleteEvent} and sends a verification email to the user.
 * <p>
 * Generates a verification token, saves it via {@link VerificationTokenService}, and sends a confirmation email using {@link EmailService}.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  private static final String CONFIRMATION_URL_TEMPLATE = "http://localhost:8080/api/v1/auth/verify-email?token=%s";

  private static final String CONFIRMATION_URL_MESSAGE = "Click the link to verify your email: <a href=\"%s\">Verify Email</a>";

  private final VerificationTokenService tokenService;

  private final EmailService emailService;

  @Override
  public void onApplicationEvent(@NonNull OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    var user = event.getUser();
    var token = UUID.randomUUID().toString();
    tokenService.createVerificationToken(user, token);
    var recipientAddress = user.getEmail();
    var subject = "Email Verification";
    var confirmationUrl = CONFIRMATION_URL_TEMPLATE.formatted(token);
    var message = CONFIRMATION_URL_MESSAGE.formatted(confirmationUrl);
    emailService.sendMail(recipientAddress, subject, message);
  }
}
