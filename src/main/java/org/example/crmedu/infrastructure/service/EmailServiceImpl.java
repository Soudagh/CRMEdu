package org.example.crmedu.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.crmedu.domain.service.auth.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Override
  @SneakyThrows
  public void sendMail(String to, String subject, String text) {
    var message = mailSender.createMimeMessage();
    var messageHelper = new MimeMessageHelper(message, true);
    messageHelper.setTo(to);
    messageHelper.setSubject(subject);
    messageHelper.setText(text, true);
    mailSender.send(message);
  }
}
