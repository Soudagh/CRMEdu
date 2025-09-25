package org.example.crmedu.controller.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.user.CreateUserRequest;
import org.example.crmedu.application.dto.request.user.UpdateUserRequest;
import org.example.crmedu.controller.MockCreator;
import org.example.crmedu.domain.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for {@code UserController}. This class verifies the functionality of user-related API endpoints.
 */
@WithMockUser(roles = "SUPERUSER")
public class UserControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MockCreator mockCreator;

  @Test
  @SneakyThrows
  void shouldCreateUser() {
    var organizationId = mockCreator.createOrganization().getId();
    var request = getMockObject(CreateUserRequest.class).setPassword("password1").setPhone("+79999999999").setEmail("org@mail.ru").setOrganization(organizationId)
        .setRole(Role.SUPERUSER);
    mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(request.getName()));
  }

  @Test
  @SneakyThrows
  void shouldGetUser() {
    var response = mockCreator.createUser();
    var id = response.getId();
    mockMvc.perform(get("/api/v1/users/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value(response.getName()));
  }

  @Test
  @SneakyThrows
  void shouldReturnNotFoundWhenUserDoesNotExist() {
    var nonExistentId = 999999L;
    mockMvc.perform(get("/api/v1/suers/" + nonExistentId))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void shouldGetUsers() {
    mockMvc.perform(get("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  @SneakyThrows
  void shouldUpdateUser() {
    var createResponse = mockCreator.createUser();
    var id = createResponse.getId();
    var updateRequest = getMockObject(UpdateUserRequest.class).setPassword("password2").setPhone("+79999999999").setEmail("org@mail.ru")
        .setOrganization(createResponse.getOrganization());
    mockMvc.perform(put("/api/v1/users/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldDeleteUser() {
    var response = mockCreator.createUser();
    var id = response.getId();
    mockMvc.perform(delete("/api/v1/users/" + id))
        .andExpect(status().isOk());
  }
}
