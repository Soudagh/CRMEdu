package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.request.homework.CreateHomeworkRequest;
import org.example.crmedu.application.dto.request.homework.GradeHomeworkRequest;
import org.example.crmedu.application.dto.request.homework.SubmitAnswerRequest;
import org.example.crmedu.application.dto.request.homework.UpdateHomeworkRequest;
import org.example.crmedu.application.dto.response.homework.GetCheckedTaskResponse;
import org.example.crmedu.application.dto.response.homework.GetCompletedTaskResponse;
import org.example.crmedu.application.dto.response.homework.GetHomeworkResponse;
import org.example.crmedu.application.mapping.HomeworkDTOMapper;
import org.example.crmedu.domain.service.homework.HomeworkService;
import org.example.crmedu.domain.service.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/homework")
public class HomeworkController {

  private final HomeworkService homeworkService;
  private final HomeworkDTOMapper mapper;
  private final JwtService jwtService;

  @PostMapping
  @Secured({"SUPERUSER", "ORG_ADMIN", "TUTOR"})
  public ResponseEntity<GetHomeworkResponse> createDraft(@Valid @RequestBody CreateHomeworkRequest request) {
    var currentUser = jwtService.getCurrentUser();
    var homework = homeworkService.createDraft(mapper.createRequestToHomework(request), currentUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.homeworkToGetResponse(homework));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetHomeworkResponse> getHomework(@PathVariable Long id) {
    var currentUser = jwtService.getCurrentUser();
    var homework = homeworkService.findByIdWithAccessCheck(id, currentUser);
    return ResponseEntity.ok(mapper.homeworkToGetResponse(homework));
  }

  @PutMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN", "TUTOR"})
  public ResponseEntity<Void> updateDraft(@Valid @RequestBody UpdateHomeworkRequest request, @PathVariable Long id) {
    var currentUser = jwtService.getCurrentUser();
    homeworkService.updateDraft(mapper.updateRequestToHomework(request), id, currentUser);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN", "TUTOR"})
  public ResponseEntity<Void> deleteDraft(@PathVariable Long id) {
    var currentUser = jwtService.getCurrentUser();
    homeworkService.deleteDraft(id, currentUser);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/publish")
  @Secured({"SUPERUSER", "ORG_ADMIN", "TUTOR"})
  public ResponseEntity<GetHomeworkResponse> publish(@PathVariable Long id) {
    var currentUser = jwtService.getCurrentUser();
    var homework = homeworkService.publish(id, currentUser);
    return ResponseEntity.ok(mapper.homeworkToGetResponse(homework));
  }

  @GetMapping("/tutor")
  @Secured({"TUTOR"})
  public ResponseEntity<List<GetHomeworkResponse>> getHomeworkForTutor() {
    var userId = jwtService.getCurrentUser().getId();
    var homeworks = homeworkService.getTutorHomeworks(userId);
    return ResponseEntity.ok(mapper.homeworksToGetResponses(homeworks));
  }

  @GetMapping("/student")
  @Secured({"STUDENT"})
  public ResponseEntity<List<GetHomeworkResponse>> getHomeworkForStudent() {
    var userId = jwtService.getCurrentUser().getId();
    var homeworks = homeworkService.getHomeworkForStudent(userId);
    return ResponseEntity.ok(mapper.homeworksToGetResponses(homeworks));
  }

  @GetMapping("/{id}/submissions")
  @Secured({"SUPERUSER", "ORG_ADMIN", "TUTOR"})
  public ResponseEntity<List<GetCompletedTaskResponse>> getSubmissionsForReview(@PathVariable Long id) {
    var currentUser = jwtService.getCurrentUser();
    var submissions = homeworkService.getSubmissionsForReview(id, currentUser);
    return ResponseEntity.ok(mapper.completedTasksToGetResponses(submissions));
  }

  @PostMapping("/{id}/submit")
  @Secured({"STUDENT"})
  public ResponseEntity<GetCompletedTaskResponse> submitAnswer(
      @PathVariable Long id, @Valid @RequestBody SubmitAnswerRequest request) {
    var userId = jwtService.getCurrentUser().getId();
    var completed = homeworkService.submitAnswer(id, userId, request.getAnswer(), request.getAttachmentUrls());
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.completedTaskToGetResponse(completed));
  }

  @PostMapping("/submissions/{completedTaskId}/grade")
  @Secured({"SUPERUSER", "ORG_ADMIN", "TUTOR"})
  public ResponseEntity<GetCheckedTaskResponse> gradeSubmission(
      @PathVariable Long completedTaskId, @Valid @RequestBody GradeHomeworkRequest request) {
    var currentUser = jwtService.getCurrentUser();
    var checked = homeworkService.gradeSubmission(
        completedTaskId, currentUser, request.getStatus(), request.getComments());
    return ResponseEntity.ok(mapper.checkedTaskToGetResponse(checked));
  }

  @GetMapping("/{id}/my-submission")
  @Secured({"STUDENT"})
  public ResponseEntity<GetCompletedTaskResponse> getMySubmission(@PathVariable Long id) {
    var userId = jwtService.getCurrentUser().getId();
    var submission = homeworkService.getStudentSubmission(id, userId);
    return ResponseEntity.ok(mapper.completedTaskToGetResponse(submission));
  }
}