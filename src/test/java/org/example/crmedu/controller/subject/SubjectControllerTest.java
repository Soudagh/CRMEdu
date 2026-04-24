package org.example.crmedu.controller.subject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.subject.CreateSubjectRequest;
import org.example.crmedu.application.dto.request.subject.UpdateSubjectRequest;
import org.example.crmedu.application.dto.response.subject.CreateSubjectResponse;
import org.example.crmedu.controller.MockCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for {@code SubjectController}. This class verifies the functionality of subject-related API endpoints.
 */
@WithMockUser(roles = "SUPERUSER")
public class SubjectControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MockCreator mockCreator;

  @Test
  @SneakyThrows
  void shouldCreateSubject() {
    var organizationId = mockCreator.createOrganization().getId();
    var request = getMockObject(CreateSubjectRequest.class).setOrganization(organizationId);
    mockMvc.perform(post("/api/v1/subjects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(request.getName()));
  }

  @Test
  @SneakyThrows
  void createSubject_shouldReturnBadRequest_whenSubjectWithThisNameExists() {
    var organizationId = mockCreator.createOrganization().getId();
    var request = getMockObject(CreateSubjectRequest.class).setOrganization(organizationId);
    var subjectRequest = mockMvc.perform(post("/api/v1/subjects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(request.getName())).andReturn().getResponse().getContentAsString();
    var createdSubject = objectMapper.readValue(subjectRequest, CreateSubjectResponse.class);
    var newSubjectRequest = getMockObject(CreateSubjectRequest.class).setOrganization(organizationId).setName(createdSubject.getName());
    mockMvc.perform(post("/api/v1/subjects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newSubjectRequest)))
        .andExpect(status().isBadRequest());

  }

  @Test
  @SneakyThrows
  void shouldGetSubject() {
    var response = mockCreator.createSubject();
    var id = response.getId();
    mockMvc.perform(get("/api/v1/subjects/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value(response.getName()));
  }

  @Test
  @SneakyThrows
  void shouldGetSubjects() {
    mockMvc.perform(get("/api/v1/subjects")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  @SneakyThrows
  void shouldReturnNotFoundWhenSubjectDoesNotExist() {
    var nonExistentId = 999999L;
    mockMvc.perform(get("/api/v1/subjects/" + nonExistentId))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void shouldUpdateSubject() {
    var createResponse = mockCreator.createSubject();
    var id = createResponse.getId();
    var updateRequest = getMockObject(UpdateSubjectRequest.class);
    mockMvc.perform(put("/api/v1/subjects/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldDeleteSubject() {
    var response = mockCreator.createSubject();
    var id = response.getId();
    mockMvc.perform(delete("/api/v1/subjects/" + id))
        .andExpect(status().isOk());
  }
}
