package org.example.crmedu.application.controller;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.subject.CreateSubjectRequest;
import org.example.crmedu.application.dto.request.subject.UpdateSubjectRequest;
import org.example.crmedu.application.dto.response.subject.CreateSubjectResponse;
import org.example.crmedu.application.dto.response.subject.GetSubjectResponse;
import org.example.crmedu.application.mapping.SubjectDTOMapper;
import org.example.crmedu.domain.service.SubjectService;
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

@RestController
@RequestMapping("api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;

  private final SubjectDTOMapper subjectMapper;

  @GetMapping
  public ResponseEntity<PageDTO<GetSubjectResponse>> getSubjects(Pageable pageable) {
    var subjectsPage = subjectService.findAll(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(subjectMapper.pageSubjectToPageDTO(subjectsPage));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetSubjectResponse> getSubject(@PathVariable Long id) {
    var subject = subjectService.findById(id);
    return ResponseEntity.ok(subjectMapper.subjectToGetSubjectResponse(subject));
  }

  @PostMapping
  public ResponseEntity<CreateSubjectResponse> createSubject(@RequestBody CreateSubjectRequest request) {
    var subject = subjectService.create(subjectMapper.createRequestToSubject(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(subjectMapper.subjectToCreateSubjectResponse(subject));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateSubject(@PathVariable Long id, @RequestBody UpdateSubjectRequest request) {
    subjectService.update(subjectMapper.updateRequestToSubject(request), id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
    subjectService.delete(id);
    return ResponseEntity.ok().build();
  }

}
