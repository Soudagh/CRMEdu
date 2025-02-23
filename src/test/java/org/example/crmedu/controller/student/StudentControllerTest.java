package org.example.crmedu.controller.student;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.student.CreateStudentRequest;
import org.example.crmedu.application.dto.request.student.UpdateStudentRequest;
import org.example.crmedu.controller.MockCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class StudentControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MockCreator mockCreator;

  @Test
  @SneakyThrows
  void shouldCreateStudent() {
    var organizationId = mockCreator.createOrganization().getId();
    var request = getMockObject(CreateStudentRequest.class).setPhone("+79999999999").setEmail("org@mail.ru").setHex("#FFFFFF").setGrade(5)
        .setOrganization(organizationId);
    mockMvc.perform(post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(request.getName()));
  }

  @Test
  @SneakyThrows
  void createStudent_shouldReturnBadRequest_whenFieldsNotValid() {
    var organizationId = mockCreator.createOrganization().getId();
    var request = getMockObject(CreateStudentRequest.class).setPhone("+79999999999").setEmail("org@mail.ru").setHex("#FFFFFF").setGrade(0)
        .setOrganization(organizationId);
    mockMvc.perform(post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @SneakyThrows
  void shouldGetStudent() {
    var response = mockCreator.createStudent();
    var id = response.getId();
    mockMvc.perform(get("/api/v1/students/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value(response.getName()));
  }

  @Test
  @SneakyThrows
  void shouldReturnNotFoundWhenStudentDoesNotExist() {
    var nonExistentId = 999999L;
    mockMvc.perform(get("/api/v1/students/" + nonExistentId))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void shouldGetStudents() {
    mockMvc.perform(get("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  @SneakyThrows
  void shouldUpdateStudent() {
    var createResponse = mockCreator.createStudent();
    var id = createResponse.getId();
    var updateRequest = getMockObject(UpdateStudentRequest.class).setPhone("+79999999999").setEmail("org@mail.ru").setHex("#FFFFFF").setGrade(5);
    mockMvc.perform(put("/api/v1/students/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldDeleteStudent() {
    var response = mockCreator.createStudent();
    var id = response.getId();
    mockMvc.perform(delete("/api/v1/students/" + id))
        .andExpect(status().isOk());
  }
}
