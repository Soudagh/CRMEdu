package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.student.CreateStudentRequest;
import org.example.crmedu.application.dto.request.student.UpdateStudentRequest;
import org.example.crmedu.application.dto.response.student.CreateStudentResponse;
import org.example.crmedu.application.dto.response.student.GetStudentResponse;
import org.example.crmedu.application.mapping.StudentDTOMapper;
import org.example.crmedu.domain.service.student.StudentService;
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
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {

  private final StudentService studentService;

  private final StudentDTOMapper mapper;

  @GetMapping
  ResponseEntity<PageDTO<GetStudentResponse>> getStudents(Pageable pageable) {
    var page = studentService.getStudents(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(mapper.pageStudentToPageDtoGetTutorResponse(page));
  }

  @GetMapping("/{id}")
  ResponseEntity<GetStudentResponse> getStudentById(@PathVariable Long id) {
    var student = studentService.findById(id);
    return ResponseEntity.ok(mapper.studentToGetStudentResponse(student));
  }

  @PostMapping
  ResponseEntity<CreateStudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
    var student = studentService.create(mapper.createStudentRequestToStudent(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.studentToCreateStudentResponse(student));
  }

  @PutMapping("/{id}")
  ResponseEntity<Void> updateStudent(@Valid @RequestBody UpdateStudentRequest request, @PathVariable Long id) {
    studentService.updateStudent(mapper.updateStudentRequestToStudent(request), id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok().build();
  }
}
