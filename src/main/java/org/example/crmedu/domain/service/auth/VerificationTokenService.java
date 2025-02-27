package org.example.crmedu.domain.service.auth;

import org.example.crmedu.domain.model.User;

public interface VerificationTokenService {

  void createVerificationToken(User user, String token);
}
