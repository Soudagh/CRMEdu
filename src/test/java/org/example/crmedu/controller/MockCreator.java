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


/**
 * Utility class for creating mock entities via API requests during integration testing. It provides methods for creating different domain entities by making
 * HTTP POST requests.
 */
public class MockCreator extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  /**
   * Creates a new organization.
   *
   * @return a {@link CreateOrganizationResponse} object containing the details of the created organization
   */
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

  /**
   * Creates a new user.
   *
   * @return a {@link CreateUserResponse} object with the created user's details.
   */
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

  /**
   * Creates a new tutor.
   *
   * @return a {@link CreateTutorResponse} object containing the tutor's details
   */
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

  /**
   * Creates a new subject.
   *
   * @return a {@link CreateSubjectResponse} object with subject details
   */
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

  /**
   * Creates a new student.
   *
   * @return a {@link CreateStudentResponse} object containing student details.
   */
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

  /**
   * Creates a new schedule.
   *
   * @return a {@link CreateTutorScheduleResponse} object containing schedule details
   */
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

  /**
   * Creates a new subject that linked to certain organization.
   *
   * @param organizationId the unique identifier of the organization
   * @return a {@link CreateSubjectResponse} object with subject details
   */
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

  /**
   * Creates new tutor that linked to certain user.
   *
   * @param userId the unique identifier of the user
   * @return a {@link CreateTutorResponse} object containing the tutor's details
   */
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
