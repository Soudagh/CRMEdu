package org.example.crmedu.domain.service.auth;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.UserRepository;
import org.springframework.stereotype.Service;


/**
 * The implementation of the {@code VerificationTokenService}. Creates new verification token for user.
 */
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
