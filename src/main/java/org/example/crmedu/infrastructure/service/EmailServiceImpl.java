package org.example.crmedu.infrastructure.service;

import jakarta.mail.MessagingException;
import org.example.crmedu.domain.service.auth.EmailService;
import org.example.crmedu.infrastructure.exception.MailSendingException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link EmailService} for sending emails.
 * <p>
 * Uses {@link JavaMailSender} to send MIME-formatted emails with support for HTML content.
 * When the mail auto-configuration is disabled the sender is absent and all calls become no-ops.
 * </p>
 */
@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  public EmailServiceImpl(ObjectProvider<JavaMailSender> mailSenderProvider) {
    this.mailSender = mailSenderProvider.getIfAvailable();
  }

  @Override
  public void sendMail(String to, String subject, String text) {
    if (mailSender == null) {
      return;
    }
    var message = mailSender.createMimeMessage();
    try {
      var messageHelper = new MimeMessageHelper(message, true);
      messageHelper.setTo(to);
      messageHelper.setSubject(subject);
      messageHelper.setText(text, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      throw new MailSendingException("Failed to compose the email message", e);
    } catch (MailException e) {
      throw new MailSendingException("Failed to send email", e);
    }
  }

}
