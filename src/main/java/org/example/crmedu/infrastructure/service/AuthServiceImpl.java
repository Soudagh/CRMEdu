package org.example.crmedu.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.event.OnRegistrationCompleteEvent;
import org.example.crmedu.domain.model.Jwt;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.service.auth.AuthService;
import org.example.crmedu.domain.service.jwt.JwtService;
import org.example.crmedu.domain.service.user.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserService userService;

  private final JwtService jwtService;

  private final ApplicationEventPublisher eventPublisher;

  @Override
  @Transactional
  public User signUp(User user) {
    var createdUser = userService.create(
        user
    );
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(createdUser));
    return createdUser;
  }

  @Override
  public Jwt signIn(User user) {
    return jwtService.login(user);
  }

  @Override
  public void verifyEmail(String token) {
    userService.verifyUserByVerificationToken(token);
  }

  @Override
  public Jwt getNewAccessToken(String refreshToken) {
    return jwtService.getAccessToken(refreshToken);
  }

  @Override
  public Jwt getNewRefreshToken(String refreshToken) {
    return jwtService.refresh(refreshToken);
  }
}
