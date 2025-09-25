package org.example.crmedu.application.controller;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.request.lesson.PatchLessonLinkRequest;
import org.example.crmedu.application.dto.request.lesson.PatchLessonNotesRequest;
import org.example.crmedu.application.dto.response.lesson.CreateLessonQrResponse;
import org.example.crmedu.application.dto.response.lesson.GetLessonResponse;
import org.example.crmedu.application.mapping.LessonDTOMapper;
import org.example.crmedu.domain.service.lesson.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lessons")
public class LessonController {

  private final LessonService lessonService;

  private final LessonDTOMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<GetLessonResponse> getLessonById(@PathVariable Long id) {
    var lesson = lessonService.findById(id);
    return ResponseEntity.ok(mapper.getLessonResponse(lesson));
  }

  @PostMapping("/{id}/qr")
  public ResponseEntity<CreateLessonQrResponse> createLessonQr(@PathVariable Long id) {
    var lessonQr = lessonService.generateQr(id);
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.lessonQrToResponse(lessonQr));
  }

  @PatchMapping("/{id}/notes")
  public ResponseEntity<Void> patchLessonNotesById(@PathVariable Long id, @RequestBody PatchLessonNotesRequest request) {
    lessonService.patchLessonNotes(request.getNotes(), id);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/link")
  public ResponseEntity<Void> patchLessonLinkById(@PathVariable Long id, @RequestBody PatchLessonLinkRequest request) {
    lessonService.patchLessonLink(request.getLink(), id);
    return ResponseEntity.ok().build();
  }
}
