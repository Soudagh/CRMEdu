package org.example.crmedu.controller.organization;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.crmedu.BaseE2ETest;
import org.example.crmedu.application.dto.request.organization.CreateOrganizationRequest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.UserRepository;
import org.example.crmedu.domain.service.auth.EmailService;
import org.example.crmedu.domain.service.jwt.PasswordEncode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class OrganizationE2ETest extends BaseE2ETest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncode passwordEncode;

  @MockitoBean
  private EmailService emailService;

  private final String superuserEmail = "superuser@crmedu.test";
  private final String superuserPassword = "superpassword";

  @BeforeEach
  void setupSuperuser() {
    var user = getMockObject(User.class).setId(null).setEmail(superuserEmail).setPassword(passwordEncode.encode(superuserPassword)).setRole(Role.SUPERUSER)
        .setOrganization(null).setHex("#444444");
    userRepository.create(user);
  }

  @Test
  void givenAuthenticatedUserByJwt_whenPostOrganizations_shouldCreateOrganization() {
    var token = loginAndGetToken(superuserEmail, superuserPassword);
    doNothing().when(emailService).sendMail(any(), any(), any());
    var request = getMockObject(CreateOrganizationRequest.class).setPhone("+79999999999").setEmail(superuserEmail).setName("Test organization");

    RestAssured.given()
        .baseUri(baseUrl())
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .body(request)
        .when()
        .post("/api/v1/organizations")
        .then()
        .statusCode(201)
        .body("name", equalTo("Test organization"));
  }

}
