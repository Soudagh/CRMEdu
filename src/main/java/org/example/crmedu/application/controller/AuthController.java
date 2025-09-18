package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.request.auth.RefreshJwtRequest;
import org.example.crmedu.application.dto.request.auth.SignInRequest;
import org.example.crmedu.application.dto.request.auth.SignUpRequest;
import org.example.crmedu.application.dto.response.auth.JwtResponse;
import org.example.crmedu.application.dto.response.auth.SignUpResponse;
import org.example.crmedu.domain.event.OnRegistrationCompleteEvent;
import org.example.crmedu.application.mapping.JwtDTOMapper;
import org.example.crmedu.application.mapping.UserDTOMapper;
import org.example.crmedu.domain.service.auth.AuthService;
import org.example.crmedu.domain.service.jwt.JwtService;
import org.example.crmedu.domain.service.user.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing authentication operations. Provides endpoints to registration, login, verification and getting new tokens.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

  private final AuthService authService;

  private final UserDTOMapper userDTOMapper;

  private final JwtDTOMapper jwtDTOMapper;

  /**
   * Sign up new user in system.
   *
   * @param signUpRequest an object containing registration details
   * @return a {@link ResponseEntity} containing the registration data in {@link SignUpResponse}
   */
  @PostMapping("/signup")
  public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    var user = authService.signUp(userDTOMapper.singUpRequestToUser(signUpRequest));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userDTOMapper.userToSignUpResponse(user));
  }

  /**
   * Verifies user via verification token.
   *
   * @param token verification token of the user
   * @return a {@link ResponseEntity} that send text "verify", if user was verified successfully
   */
  @GetMapping("/verify-email")
  public ResponseEntity<String> verifyEmail(@RequestParam String token) {
    authService.verifyEmail(token);
    return ResponseEntity.ok().build();
  }

  /**
   * Sign in user.
   *
   * @param signInRequest an object containing sign in details
   * @return a {@link ResponseEntity} containing the tokens data in {@link JwtResponse}
   */
  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
    var token = authService.signIn(userDTOMapper.singInRequestToUser(signInRequest));
    return ResponseEntity.ok(jwtDTOMapper.toJwtResponse(token));
  }

  /**
   * Updates access token and retrieves it.
   *
   * @param request an object containing details about refresh token
   * @return a {@link ResponseEntity} containing the tokens data in {@link JwtResponse}
   */
  @PostMapping("/token")
  public ResponseEntity<JwtResponse> getNewAccessToken(@Valid @RequestBody RefreshJwtRequest request) {
    var token = authService.getNewAccessToken(request.refreshToken);
    return ResponseEntity.ok(jwtDTOMapper.toJwtResponse(token));
  }

  /**
   * Updates refresh and access token and retrieves it.
   *
   * @param request an object containing details about refresh token
   * @return a {@link ResponseEntity} containing the tokens data in {@link JwtResponse}
   */
  @PostMapping("/refresh")
  public ResponseEntity<JwtResponse> getNewRefreshToken(@Valid @RequestBody RefreshJwtRequest request) {
    var token = authService.getNewRefreshToken(request.getRefreshToken());
    return ResponseEntity.ok(jwtDTOMapper.toJwtResponse(token));
  }
}
