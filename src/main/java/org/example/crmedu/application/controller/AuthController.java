package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.request.auth.RefreshJwtRequest;
import org.example.crmedu.application.dto.request.auth.SignInRequest;
import org.example.crmedu.application.dto.request.auth.SignUpRequest;
import org.example.crmedu.application.dto.response.auth.JwtResponse;
import org.example.crmedu.application.dto.response.auth.SignUpResponse;
import org.example.crmedu.application.event.OnRegistrationCompleteEvent;
import org.example.crmedu.application.mapping.JwtDTOMapper;
import org.example.crmedu.application.mapping.UserDTOMapper;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

  private final UserService userService;

  private final JwtService jwtService;

  private final UserDTOMapper userDTOMapper;

  private final JwtDTOMapper jwtDTOMapper;

  private final ApplicationEventPublisher eventPublisher;

  @PostMapping("/signup")
  public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    var user = userService.create(
        userDTOMapper
            .singUpRequestToUser(signUpRequest)
    );
    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userDTOMapper.userToSignUpResponse(user));
  }

  @GetMapping("/verify-email")
  public ResponseEntity<String> verifyEmail(@RequestParam String token) {
    userService.validateVerificationToken(token);
    return ResponseEntity.ok("verified");
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody SignInRequest signInRequest) {
    var token = jwtService.login(userDTOMapper.singInRequestToUser(signInRequest));
    return ResponseEntity.ok(jwtDTOMapper.toJwtResponse(token));
  }

  @PostMapping("/token")
  public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
    var token = jwtService.getAccessToken(request.getRefreshToken());
    return ResponseEntity.ok(jwtDTOMapper.toJwtResponse(token));
  }

  @PostMapping("/refresh")
  public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
    var token = jwtService.refresh(request.getRefreshToken());
    return ResponseEntity.ok(jwtDTOMapper.toJwtResponse(token));
  }
}
