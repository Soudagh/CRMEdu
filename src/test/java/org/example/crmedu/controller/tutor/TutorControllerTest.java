package org.example.crmedu.controller.tutor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.tutor.CreateTutorRequest;
import org.example.crmedu.application.dto.request.tutor.PatchTutorGradesRequest;
import org.example.crmedu.application.dto.request.tutor.PatchTutorSubjectsRequest;
import org.example.crmedu.application.dto.request.tutor.UpdateTutorRequest;
import org.example.crmedu.controller.MockCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class TutorControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MockCreator mockCreator;

  @Test
  @SneakyThrows
  void shouldCreateTutor() {
    var userId = mockCreator.createUser().getId();
    var request = getMockObject(CreateTutorRequest.class).setUser(userId);
    mockMvc.perform(post("/api/v1/tutors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.notes").value(request.getNotes()));
  }

  @Test
  @SneakyThrows
  void shouldGetTutor() {
    var response = mockCreator.createTutor();
    var id = response.getId();
    mockMvc.perform(get("/api/v1/tutors/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.notes").value(response.getNotes()));
  }

  @Test
  @SneakyThrows
  void shouldReturnNotFoundWhenTutorDoesNotExist() {
    var nonExistentId = 999999L;
    mockMvc.perform(get("/api/v1/tutors/" + nonExistentId))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void shouldGetTutors() {
    mockMvc.perform(get("/api/v1/tutors"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  @SneakyThrows
  void shouldUpdateTutor() {
    var oldTutor = mockCreator.createTutor();
    var id = oldTutor.getId();
    var grades = Set.of(1, 2, 3, 4);
    var updateRequest = getMockObject(UpdateTutorRequest.class).setGrades(grades).setSubjects(null);
    mockMvc.perform(put("/api/v1/tutors/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldDeleteTutor() {
    var tutor = mockCreator.createTutor();
    var id = tutor.getId();
    mockMvc.perform(delete("/api/v1/tutors/" + id))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldPatchSubjectsOfTutor() {
    var user = mockCreator.createUser();
    var organizationId = user.getOrganization();
    var firstSubject = mockCreator.createSubjectByOrganizationId(organizationId);
    var secondSubject = mockCreator.createSubjectByOrganizationId(organizationId);
    var subjects = Set.of(firstSubject.getId(), secondSubject.getId());
    var subjectRequest = getMockObject(PatchTutorSubjectsRequest.class).setSubjects(subjects);
    var tutor = mockCreator.createTutorByUserId(user.getId());
    mockMvc.perform(patch("/api/v1/tutors/" + tutor.getId() + "/subjects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(subjectRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldPatchGradesOfTutor() {
    var tutor = mockCreator.createTutor();
    var grades = Set.of(1, 2, 3);
    var gradesRequest = getMockObject(PatchTutorGradesRequest.class).setGrades(grades);
    mockMvc.perform(patch("/api/v1/tutors/" + tutor.getId() + "/grades")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(gradesRequest)))
        .andExpect(status().isOk());
  }
}
