package org.example.crmedu.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.UserRepository;
import org.example.crmedu.domain.service.auth.VerificationTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

  private final UserRepository userRepository;

  @Override
  public void createVerificationToken(User user, String token) {
    user.setVerificationToken(token);
    userRepository.save(user);
  }
}
