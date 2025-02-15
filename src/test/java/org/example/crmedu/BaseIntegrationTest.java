package org.example.crmedu;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class BaseIntegrationTest extends BaseUnitTest {

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

  @AfterAll
  static void afterAll() {
    postgreSQLContainer.stop();
  }
}
