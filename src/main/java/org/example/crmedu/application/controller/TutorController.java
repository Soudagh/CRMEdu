package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.tutor.CreateTutorRequest;
import org.example.crmedu.application.dto.request.tutor.PatchTutorGradesRequest;
import org.example.crmedu.application.dto.request.tutor.PatchTutorSubjectsRequest;
import org.example.crmedu.application.dto.request.tutor.UpdateTutorRequest;
import org.example.crmedu.application.dto.response.tutor.CreateTutorResponse;
import org.example.crmedu.application.dto.response.tutor.GetTutorResponse;
import org.example.crmedu.application.mapping.SubjectDTOMapper;
import org.example.crmedu.application.mapping.TutorDTOMapper;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

  private final TutorDTOMapper tutorMapper;

  private final SubjectDTOMapper subjectMapper;

  /**
   * Retrieves a paginates list of tutors.
   *
   * @param pageable an object specifying pagination parameters (page number and size)
   * @return a {@link ResponseEntity} containing a paginated list of tutors wrapped in {@link PageDTO} of {@link GetTutorResponse}
   */
  @GetMapping
  public ResponseEntity<PageDTO<GetTutorResponse>> getTutors(Pageable pageable) {
    var page = tutorService.findAll(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(tutorMapper.pageTutorToPageGetResponse(page));
  }

  /**
   * Creates a new tutor based on the provided request data.
   *
   * @param request an object containing the tutor details
   * @return a {@link ResponseEntity} with the created tutor data in {@link CreateTutorResponse}
   */
  @PostMapping
  public ResponseEntity<CreateTutorResponse> createTutors(@Valid @RequestBody CreateTutorRequest request) {
    var tutor = tutorService.create(tutorMapper.createRequestToTutor(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(tutorMapper.tutorToCreateResponse(tutor));
  }

  /**
   * Retrieves a tutor by its unique identifier.
   *
   * @param id the unique identifier of the tutor
   * @return a {@link ResponseEntity} containing the tutor data in {@link GetTutorResponse}
   */
  @GetMapping("/{id}")
  public ResponseEntity<GetTutorResponse> getTutor(@PathVariable Long id) {
    return ResponseEntity.ok(tutorMapper.tutorToGetResponse(tutorService.findById(id)));
  }

  /**
   * Updates an existing tutor by its unique identifier.
   *
   * @param request an object containing the updated tutor details
   * @param id the unique identifier of the tutor to update
   * @return a {@link ResponseEntity}
   */
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateTutor(@Valid @RequestBody UpdateTutorRequest request, @PathVariable Long id) {
    tutorService.update(tutorMapper.updateRequestToUser(request), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a tutor by its unique identifier.
   *
   * @param id the unique identifier of the tutor to delete
   * @return a {@link ResponseEntity}
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
    tutorService.delete(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Updates the list of subjects that a tutor can teach.
   *
   * @param subjects the subjects set
   * @param id the unique identifier of the tutor to update
   * @return a {@link ResponseEntity}
   */
  @PatchMapping("/{id}/subjects")
  ResponseEntity<Void> updateSubjects(@Valid @RequestBody PatchTutorSubjectsRequest subjects, @PathVariable Long id) {
    tutorService.patchSubjects(tutorMapper.patchTutorsSubjectRequestToSubjectSet(subjects, subjectMapper), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Updates the list of education classes that a tutor can teach.
   *
   * @param grades the grades set
   * @param id the unique identifier of the tutor to update
   * @return a {@link ResponseEntity}
   */
  @PatchMapping("/{id}/grades")
  ResponseEntity<Void> updateGrades(@Valid @RequestBody PatchTutorGradesRequest grades, @PathVariable Long id) {
    tutorService.patchGrades(tutorMapper.patchTutorsGradesRequestToGradesSet(grades), id);
    return ResponseEntity.ok().build();
  }

}
