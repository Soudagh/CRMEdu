package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.schedule.CreateTutorScheduleRequest;
import org.example.crmedu.application.dto.request.schedule.UpdateTutorScheduleRequest;
import org.example.crmedu.application.dto.response.schedule.CreateTutorScheduleResponse;
import org.example.crmedu.application.dto.response.schedule.GetTutorScheduleResponse;
import org.example.crmedu.application.mapping.TutorScheduleDTOMapper;
import org.example.crmedu.domain.service.schedule.TutorScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing tutor's schedules. Provides endpoints to create, retrieve, update and delete schedules.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/schedules")
public class TutorScheduleController {

  private final TutorScheduleDTOMapper mapper;

  private final TutorScheduleService tutorScheduleService;

  /**
   * Creates a new schedule based on the provided request data.
   *
   * @param request an object containing the request data
   * @param tutorId the unique identifier of tutor
   * @return a {@link ResponseEntity} with the created schedule data in {@link CreateTutorScheduleResponse}
   */
  @PostMapping("/tutor/{tutorId}")
  ResponseEntity<CreateTutorScheduleResponse> createSchedule(@Valid @RequestBody CreateTutorScheduleRequest request, @PathVariable Long tutorId) {
    var schedule = tutorScheduleService.createSchedule(mapper.createTutorScheduleRequestToTutorSchedule(request), tutorId);
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.tutorScheduleToCreateTutorResponse(schedule));
  }

  /**
   * Retrieves a schedule by its unique identifier.
   *
   * @param id the unique identifier of the schedule
   * @return a {@link ResponseEntity} containing the schedule data in {@link GetTutorScheduleResponse}
   */
  @GetMapping("/{id}")
  ResponseEntity<GetTutorScheduleResponse> getTutorScheduleById(@PathVariable Long id) {
    var schedule = tutorScheduleService.findById(id);
    return ResponseEntity.ok(mapper.tutorScheduleToGetTutorScheduleResponse(schedule));
  }

  /**
   * Retrieves schedules of certain tutor.
   *
   * @param pageable an object specifying pagination parameters (page number and size)
   * @param tutorId the unique identifier of tutor
   * @return a {@link ResponseEntity} containing the schedule data in {@link PageDTO} of {@link GetTutorScheduleResponse}
   */
  @GetMapping("/tutor/{tutorId}")
  ResponseEntity<PageDTO<GetTutorScheduleResponse>> getTutorSchedulesOfTutor(Pageable pageable, @PathVariable Long tutorId) {
    var page = tutorScheduleService.getTutorSchedules(pageable.getPageNumber(), pageable.getPageSize(), tutorId);
    return ResponseEntity.ok(mapper.pageTutorScheduleToPageDTOGetTutorSchedule(page));
  }

  /**
   * Updates an existing schedule by its unique identifier.
   *
   * @param request an object containing the updated schedule details
   * @param id the unique identifier of the schedule to update
   * @return a {@link ResponseEntity}
   */
  @PutMapping("{id}")
  ResponseEntity<Void> updateSchedule(@Valid @RequestBody UpdateTutorScheduleRequest request, @PathVariable Long id) {
    tutorScheduleService.update(mapper.updateTutorScheduleRequestToTutorSchedule(request), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a schedule by its unique identifier.
   *
   * @param id the unique identifier of the schedule to delete
   * @return a {@link ResponseEntity}
   */
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
    tutorScheduleService.delete(id);
    return ResponseEntity.ok().build();
  }
}
