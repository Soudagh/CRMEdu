package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.subject.CreateSubjectRequest;
import org.example.crmedu.application.dto.request.subject.UpdateSubjectRequest;
import org.example.crmedu.application.dto.response.subject.CreateSubjectResponse;
import org.example.crmedu.application.dto.response.subject.GetSubjectResponse;
import org.example.crmedu.application.mapping.SubjectDTOMapper;
import org.example.crmedu.domain.service.subject.SubjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing subjects. Provides endpoints to create, retrieve, update and delete subjects.
 */
@RestController
@RequestMapping("api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;

  private final SubjectDTOMapper subjectMapper;

  /**
   * Retrieves a paginated list of subjects.
   *
   * @param pageable an object specifying pagination parameters (page number and size)
   * @return a {@link ResponseEntity} containing a paginated list of subjects wrapped in {@link PageDTO} of {@link GetSubjectResponse}
   */
  @GetMapping
  @Secured({"SUPERUSER", "ORG_ADMIN", "CURATOR"})
  public ResponseEntity<PageDTO<GetSubjectResponse>> getSubjects(Pageable pageable) {
    var subjectsPage = subjectService.findAll(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(subjectMapper.pageSubjectToPageDTO(subjectsPage));
  }

  /**
   * Retrieves a subject by its unique identifier.
   *
   * @param id the unique identifier of the subject
   * @return a {@link ResponseEntity} containing the subject data in {@link GetSubjectResponse}
   */
  @GetMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN", "CURATOR"})
  public ResponseEntity<GetSubjectResponse> getSubject(@PathVariable Long id) {
    var subject = subjectService.findById(id);
    return ResponseEntity.ok(subjectMapper.subjectToGetSubjectResponse(subject));
  }

  /**
   * Creates a new subject based on the provided request data.
   *
   * @param request an object containing the subject details
   * @return a {@link ResponseEntity} with the created subject data in {@link CreateSubjectResponse}
   */
  @PostMapping
  @Secured({"SUPERUSER", "ORG_ADMIN", "CURATOR"})
  public ResponseEntity<CreateSubjectResponse> createSubject(@Valid @RequestBody CreateSubjectRequest request) {
    var subject = subjectService.create(subjectMapper.createRequestToSubject(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(subjectMapper.subjectToCreateSubjectResponse(subject));
  }

  /**
   * Updates an existing subject by its unique identifier.
   *
   * @param id the unique identifier of the subject to update
   * @param request an object containing the updated subject details
   * @return a {@link ResponseEntity}
   */
  @PutMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN", "CURATOR"})
  public ResponseEntity<Void> updateSubject(@PathVariable Long id, @Valid @RequestBody UpdateSubjectRequest request) {
    subjectService.update(subjectMapper.updateRequestToSubject(request), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a subject by its unique identifier.
   *
   * @param id the unique identifier of the subject to delete
   * @return a {@link ResponseEntity}
   */
  @DeleteMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN", "CURATOR"})
  public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
    subjectService.delete(id);
    return ResponseEntity.ok().build();
  }
}
