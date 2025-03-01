package org.example.crmedu.controller.auth;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.auth.RefreshJwtRequest;
import org.example.crmedu.application.dto.request.auth.SignInRequest;
import org.example.crmedu.application.dto.request.auth.SignUpRequest;
import org.example.crmedu.application.dto.response.auth.JwtResponse;
import org.example.crmedu.application.dto.response.auth.SignUpResponse;
import org.example.crmedu.controller.MockCreator;
import org.example.crmedu.domain.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = "SUPERUSER")
public class AuthControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MockCreator mockCreator;

  @Autowired
  private UserService userService;

  @Test
  @SneakyThrows
  void shouldSignUpUser() {
    var organization = mockCreator.createOrganization();
    var request = getMockObject(SignUpRequest.class).setPhone("+79999999999").setEmail("org@mail.ru").setPassword("password1")
        .setOrganization(organization.getId());
    mockMvc.perform(post("/api/v1/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.email").value(request.getEmail()));
  }

  @Test
  @SneakyThrows
  void shouldLoginUser() {
    var email = "org@mail.ru";
    var password = "password1";
    signUpByEmailAndPasswordAndGetResponse(email, password);
    var signInRequest = getMockObject(SignInRequest.class).setEmail(email).setPassword(password);
    mockMvc.perform(post("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(signInRequest))
    ).andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldVerifyUser() {
    var email = "org@mail.ru";
    var password = "password1";
    var response = signUpByEmailAndPasswordAndGetResponse(email, password);
    var user = userService.findByEmail(response.getEmail());
    var verificationToken = user.getVerificationToken();
    mockMvc.perform(get("/api/v1/auth/verify-email?token=" + verificationToken))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldGetNewAccessToken() {
    var email = "org@mail.ru";
    var password = "password1";
    var loggedInData = signInAndGetResponse(email, password);
    var refreshJwtRequest = getMockObject(RefreshJwtRequest.class).setRefreshToken(loggedInData.getRefreshToken());
    Thread.sleep(1000);
    mockMvc.perform(post("/api/v1/auth/token")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(refreshJwtRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken", not(equalTo(loggedInData.getAccessToken())))).andReturn().getResponse().getContentAsString();
  }

  @Test
  @SneakyThrows
  void shouldGetNewRefreshToken() {
    var email = "org@mail.ru";
    var password = "password1";
    var loggedInData = signInAndGetResponse(email, password);
    var refreshJwtRequest = getMockObject(RefreshJwtRequest.class).setRefreshToken(loggedInData.getRefreshToken());
    mockMvc.perform(post("/api/v1/auth/refresh")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(refreshJwtRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.refreshToken", not(equalTo(loggedInData.getRefreshToken())))).andReturn().getResponse().getContentAsString();
  }

  @SneakyThrows
  private SignUpResponse signUpByEmailAndPasswordAndGetResponse(String email, String password) {
    var organization = mockCreator.createOrganization();
    var signUpRequest = getMockObject(SignUpRequest.class).setPhone("+79999999999").setEmail(email).setPassword(password)
        .setOrganization(organization.getId());
    var responseContent = mockMvc.perform(post("/api/v1/auth/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(signUpRequest))).andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, SignUpResponse.class);
  }

  @SneakyThrows
  private JwtResponse signInAndGetResponse(String email, String password) {
    signUpByEmailAndPasswordAndGetResponse(email, password);
    var signInRequest = getMockObject(SignInRequest.class).setEmail(email).setPassword(password);
    var responseContent = mockMvc.perform(post("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(signInRequest))
    ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, JwtResponse.class);
  }
}
