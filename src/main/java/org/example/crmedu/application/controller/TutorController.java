package org.example.crmedu.application.controller;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.tutor.CreateTutorRequest;
import org.example.crmedu.application.dto.request.tutor.UpdateTutorRequest;
import org.example.crmedu.application.dto.response.tutor.CreateTutorResponse;
import org.example.crmedu.application.dto.response.tutor.GetTutorResponse;
import org.example.crmedu.application.dto.response.user.CreateUserResponse;
import org.example.crmedu.application.dto.response.user.GetUserResponse;
import org.example.crmedu.application.mapping.TutorDTOMapper;
import org.example.crmedu.domain.service.tutor.TutorService;
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
 * REST controller for managing tutors. Provides endpoints to create, retrieve, update and delete tutors.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tutors")
public class TutorController {

  private final TutorService tutorService;

  private final TutorDTOMapper mapper;

  /**
   * Retrieves a paginates list of tutors.
   *
   * @param pageable an object specifying pagination parameters (page number and size)
   * @return a {@link ResponseEntity} containing a paginated list of tutors wrapped in {@link PageDTO} of {@link GetUserResponse}
   */
  @GetMapping
  public ResponseEntity<PageDTO<GetTutorResponse>> getTutors(Pageable pageable) {
    var page = tutorService.findAll(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(mapper.pageTutorToPageGetResponse(page));
  }

  /**
   * Creates a new user based on the provided request data.
   *
   * @param request an object containing the user details
   * @return a {@link ResponseEntity} with the created subject data in {@link CreateUserResponse}
   */
  @PostMapping
  public ResponseEntity<CreateTutorResponse> createTutors(@RequestBody CreateTutorRequest request) {
    var tutor = tutorService.create(mapper.createRequestToTutor(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.tutorToCreateResponse(tutor));
  }

  /**
   * Retrieves a user by its unique identifier.
   *
   * @param id the unique identifier of the user
   * @return a {@link ResponseEntity} containing the subject data in {@link GetUserResponse}
   */
  @GetMapping("/{id}")
  public ResponseEntity<GetTutorResponse> getTutor(@PathVariable Long id) {
    return ResponseEntity.ok(mapper.tutorToGetResponse(tutorService.findById(id)));
  }

  /**
   * Updates an existing user by its unique identifier.
   *
   * @param request an object containing the updated user details
   * @param id the unique identifier of the user to update
   * @return a {@link ResponseEntity}
   */
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateTutor(@RequestBody UpdateTutorRequest request, @PathVariable Long id) {
    tutorService.update(mapper.updateRequestToUser(request), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a user by its unique identifier.
   *
   * @param id the unique identifier of the user to delete
   * @return a {@link ResponseEntity}
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
    tutorService.delete(id);
    return ResponseEntity.ok().build();
  }
}
