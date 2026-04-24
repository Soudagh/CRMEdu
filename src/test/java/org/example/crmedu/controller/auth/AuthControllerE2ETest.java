package org.example.crmedu.controller.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.crmedu.BaseE2ETest;
import org.example.crmedu.domain.service.auth.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class AuthControllerE2ETest extends BaseE2ETest {

  @MockitoBean
  private EmailService emailService;

  @Test
  void givenNoToken_whenCallSecuredEndpoint_shouldReturn403Forbidden() {
    RestAssured.given()
        .baseUri(baseUrl())
        .contentType(ContentType.JSON)
        .when()
        .get("/api/v1/organizations")
        .then()
        .statusCode(403);
  }

  @Test
  void givenMalformedToken_whenCallSecuredEndpoint_shouldReturn500WithMalformed() {
    var malformedToken = "Bearer " + "malformed.jwt.token";

    RestAssured.given()
        .baseUri(baseUrl())
        .contentType(ContentType.JSON)
        .header("Authorization", malformedToken)
        .when()
        .get("/api/v1/organizations")
        .then()
        .statusCode(500);
  }
}
