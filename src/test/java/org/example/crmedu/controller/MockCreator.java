package org.example.crmedu.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.organization.CreateOrganizationRequest;
import org.example.crmedu.application.dto.request.schedule.CreateTutorScheduleRequest;
import org.example.crmedu.application.dto.request.student.CreateStudentRequest;
import org.example.crmedu.application.dto.request.subject.CreateSubjectRequest;
import org.example.crmedu.application.dto.request.tutor.CreateTutorRequest;
import org.example.crmedu.application.dto.request.user.CreateUserRequest;
import org.example.crmedu.application.dto.response.organization.CreateOrganizationResponse;
import org.example.crmedu.application.dto.response.schedule.CreateTutorScheduleResponse;
import org.example.crmedu.application.dto.response.student.CreateStudentResponse;
import org.example.crmedu.application.dto.response.subject.CreateSubjectResponse;
import org.example.crmedu.application.dto.response.tutor.CreateTutorResponse;
import org.example.crmedu.application.dto.response.user.CreateUserResponse;
import org.example.crmedu.domain.enums.DaysOfWeek;
import org.example.crmedu.domain.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


public class MockCreator extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @SneakyThrows
  public CreateOrganizationResponse createOrganization() {
    var request = getMockObject(CreateOrganizationRequest.class).setPhone("+79999999999").setEmail("org@mail.ru");
    var responseContent = mockMvc.perform(post("/api/v1/organizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    
    return objectMapper.readValue(responseContent, CreateOrganizationResponse.class);
  }

  @SneakyThrows
  public CreateUserResponse createUser() {
    var organizationId = createOrganization().getId();
    var request = getMockObject(CreateUserRequest.class).setPhone("+79999999999").setEmail("org@mail.ru").setOrganization(organizationId)
        .setRole(Role.SUPERUSER);
    var responseContent = mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, CreateUserResponse.class);
  }

  @SneakyThrows
  public CreateTutorResponse createTutor() {
    var userId = createUser().getId();
    var request = getMockObject(CreateTutorRequest.class).setUser(userId);
    var responseContent = mockMvc.perform(post("/api/v1/tutors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, CreateTutorResponse.class);
  }

  @SneakyThrows
  public CreateSubjectResponse createSubject() {
    var organizationId = createOrganization().getId();
    var request = getMockObject(CreateSubjectRequest.class).setOrganization(organizationId);
    var responseContent = mockMvc.perform(post("/api/v1/subjects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, CreateSubjectResponse.class);
  }

  @SneakyThrows
  public CreateStudentResponse createStudent() {
    var organizationId = createOrganization().getId();
    var request = getMockObject(CreateStudentRequest.class).setPhone("+79999999999").setEmail("org@mail.ru").setHex("#FFFFFF").setGrade(5)
        .setOrganization(organizationId);
    var responseContent = mockMvc.perform(post("/api/v1/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, CreateStudentResponse.class);
  }

  @SneakyThrows
  public CreateTutorScheduleResponse createTutorSchedule() {
    var tutorId = createTutor().getId();
    var request = getMockObject(CreateTutorScheduleRequest.class).setDayOfWeek(DaysOfWeek.SUNDAY).setTimeStart("07:30:00+03:00").setTimeEnd("09:30:00+03:00");
    var responseContent = mockMvc.perform(post("/api/v1/schedules/tutor/" + tutorId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, CreateTutorScheduleResponse.class);
  }

  @SneakyThrows
  public CreateSubjectResponse createSubjectByOrganizationId(Long organizationId) {
    var request = getMockObject(CreateSubjectRequest.class).setOrganization(organizationId);
    var responseContent = mockMvc.perform(post("/api/v1/subjects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, CreateSubjectResponse.class);
  }

  @SneakyThrows
  public CreateTutorResponse createTutorByUserId(Long userId) {
    var request = getMockObject(CreateTutorRequest.class).setUser(userId);
    var responseContent = mockMvc.perform(post("/api/v1/tutors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return objectMapper.readValue(responseContent, CreateTutorResponse.class);
  }
}
