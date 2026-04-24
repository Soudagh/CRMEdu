package org.example.crmedu;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.example.crmedu.application.dto.request.auth.SignInRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class BaseE2ETest extends BaseUnitTest {

  @LocalServerPort
  protected int port;

  protected static final String postgresImage = "postgres:11.14";

  protected static final PostgreSQLContainer<?> postgreSQLContainer;

  static {
    //noinspection resource
    postgreSQLContainer = new PostgreSQLContainer<>(postgresImage)
        .withDatabaseName("test-db")
        .withUsername("sa")
        .withPassword("sa");
    postgreSQLContainer.withInitScript("createSchema.sql");
    postgreSQLContainer.withReuse(false);
  }

  @DynamicPropertySource
  static void postgresqlProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", ((PostgreSQLContainer<?>) postgreSQLContainer)::getJdbcUrl);
    registry.add("spring.datasource.password", ((PostgreSQLContainer<?>) postgreSQLContainer)::getPassword);
    registry.add("spring.datasource.username", ((PostgreSQLContainer<?>) postgreSQLContainer)::getUsername);
  }

  @BeforeAll
  static void beforeAll() {
    postgreSQLContainer.start();
  }

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  protected String baseUrl() {
    return "http://localhost:" + port;
  }

  @SneakyThrows
  @AfterEach
  void clearAll() {
    JdbcTestUtils.deleteFromTables(jdbcTemplate, "crmedu.tutor", "crmedu.user", "crmedu.organization");
  }


  protected String loginAndGetToken(String email, String password) {
    var signInRequest = new SignInRequest();
    signInRequest.setEmail(email);
    signInRequest.setPassword(password);

    return RestAssured.given()
        .baseUri(baseUrl())
        .contentType(ContentType.JSON)
        .body(signInRequest)
        .when()
        .post("/api/v1/auth/login")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getString("accessToken");
  }
}
