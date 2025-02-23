package org.example.crmedu.controller.schedule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.crmedu.BaseIntegrationTest;
import org.example.crmedu.application.dto.request.schedule.CreateTutorScheduleRequest;
import org.example.crmedu.application.dto.request.schedule.UpdateTutorScheduleRequest;
import org.example.crmedu.application.dto.response.schedule.CreateTutorScheduleResponse;
import org.example.crmedu.controller.MockCreator;
import org.example.crmedu.domain.enums.DaysOfWeek;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class ScheduleControllerTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MockCreator mockCreator;

  @Test
  @SneakyThrows
  void shouldCreateSchedule() {
    var tutorId = mockCreator.createTutor().getId();
    var request = getMockObject(CreateTutorScheduleRequest.class).setDayOfWeek(DaysOfWeek.SUNDAY).setTimeStart("07:30:00+03:00").setTimeEnd("09:30:00+03:00");
    mockMvc.perform(post("/api/v1/schedules/tutor/" + tutorId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.dayOfWeek").value(DaysOfWeek.SUNDAY.toString()));
  }

  @Test
  @SneakyThrows
  void createSchedule_shouldReturnBadRequest_whenScheduleOverlaps() {
    var createdSchedule = mockCreator.createTutorSchedule();
    var request = getMockObject(CreateTutorScheduleRequest.class).setDayOfWeek(createdSchedule.getDayOfWeek()).setTimeStart(createdSchedule.getTimeStart())
        .setTimeEnd(createdSchedule.getTimeEnd());
    mockMvc.perform(post("/api/v1/schedules/tutor/" + createdSchedule.getTutorId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  @SneakyThrows
  void shouldGetSchedule() {
    var response = mockCreator.createTutorSchedule();
    var id = response.getId();
    mockMvc.perform(get("/api/v1/schedules/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.dayOfWeek").value(DaysOfWeek.SUNDAY.toString()));
  }

  @Test
  @SneakyThrows
  void shouldReturnNotFoundWhenScheduleDoesNotExist() {
    var nonExistentId = 999999L;
    mockMvc.perform(get("/api/v1/schedules/" + nonExistentId))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void shouldGetSchedules() {
    var tutorId = mockCreator.createTutor().getId();
    mockMvc.perform(get("/api/v1/schedules/tutor/" + tutorId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray());
  }

  @Test
  @SneakyThrows
  void shouldUpdateSchedule() {
    var oldSchedule = mockCreator.createTutorSchedule();
    var id = oldSchedule.getId();
    var updateRequest = getMockObject(UpdateTutorScheduleRequest.class).setDayOfWeek(DaysOfWeek.MONDAY).setTimeStart("10:30:00+03:00")
        .setTimeEnd("11:30:00+03:00");
    mockMvc.perform(put("/api/v1/schedules/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void shouldDeleteSchedule() {
    var schedule = mockCreator.createTutorSchedule();
    var id = schedule.getId();
    mockMvc.perform(delete("/api/v1/schedules/" + id))
        .andExpect(status().isOk());
  }
}
