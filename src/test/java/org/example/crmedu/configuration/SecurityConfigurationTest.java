package org.example.crmedu.configuration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.infrastructure.configuration.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class SecurityConfigurationTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @InjectMocks
  private SecurityConfig securityConfig;

  @Test
  @SneakyThrows
  void shouldReturnErrorResponseForErrorEndpoint() {
    mockMvc.perform(get("/error"))
        .andExpect(status().isInternalServerError()) // Ожидаем статус 500
        .andExpect(jsonPath("$.status").value(999))
        .andExpect(jsonPath("$.error").value("None"))
        .andExpect(jsonPath("$.timestamp").exists());
  }
}
