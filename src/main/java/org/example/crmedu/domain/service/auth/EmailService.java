package org.example.crmedu.domain.service.auth;

public interface EmailService {

  void sendMail(String to, String subject, String text);
}
