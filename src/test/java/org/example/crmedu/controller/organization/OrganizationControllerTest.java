package org.example.crmedu.controller.organization;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.organization.CreateOrganizationRequest;
import org.example.crmedu.application.dto.request.organization.UpdateOrganizationRequest;
import org.example.crmedu.controller.MockCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class OrganizationControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MockCreator mockCreator;

  @Test
  @SneakyThrows
  void shouldCreateOrganization() {
    var request = getMockObject(CreateOrganizationRequest.class).setPhone("+79999999999").setEmail("org@mail.ru");
    request.setName("Test Organization");
    mockMvc.perform(post("/api/v1/organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("Test Organization"));
  }

  @Test
  @SneakyThrows
  void createOrganization_shouldReturnBadRequest_whenEmailIsNull() {
    var request = getMockObject(CreateOrganizationRequest.class).setPhone("+79999999999").setEmail(null);
    request.setName("Test Organization");
    mockMvc.perform(post("/api/v1/organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @SneakyThrows
  void shouldGetOrganization() {
    var response = mockCreator.createOrganization();
    var id = response.getId();
    mockMvc.perform(get("/api/v1/organizations/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id));
  }

  @Test
  @SneakyThrows
  void shouldReturnNotFoundWhenOrganizationDoesNotExist() {
    var nonExistentId = 999999L;
    mockMvc.perform(get("/api/v1/organizations/" + nonExistentId))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void shouldGetOrganizations() {
    mockMvc.perform(get("/api/v1/organizations")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  @SneakyThrows
  void shouldUpdateOrganization() {
    var createResponse = mockCreator.createOrganization();
    var id = createResponse.getId();
    var updateRequest = getMockObject(UpdateOrganizationRequest.class).setPhone("+79999999999").setEmail("org@mail.ru");
    mockMvc.perform(put("/api/v1/organizations/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldDeleteOrganization() {
    var response = mockCreator.createOrganization();
    var id = response.getId();
    mockMvc.perform(delete("/api/v1/organizations/" + id))
        .andExpect(status().isOk());
  }
}
