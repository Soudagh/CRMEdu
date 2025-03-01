package org.example.crmedu.domain.service.auth;

/**
 * Service interface for mail senders.
 */
public interface EmailService {

  /**
   * Sends email to specific mail-address.
   *
   * @param to an email address of the consumer
   * @param subject the subject of the mail
   * @param text the text of the mail
   */
  void sendMail(String to, String subject, String text);
}
