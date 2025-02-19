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

/**
 * REST controller for managing students. Provides endpoints to create, retrieve, update and delete students.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentController {

  private final StudentService studentService;

  private final StudentDTOMapper mapper;

  /**
   * Retrieves a paginates list of students.
   *
   * @param pageable an object specifying pagination parameters (page number and size)
   * @return a {@link ResponseEntity} containing a paginated list of students wrapped in {@link PageDTO} of {@link GetStudentResponse}
   */
  @GetMapping
  ResponseEntity<PageDTO<GetStudentResponse>> getStudents(Pageable pageable) {
    var page = studentService.getStudents(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(mapper.pageStudentToPageDtoGetTutorResponse(page));
  }

  /**
   * Retrieves a student by its unique identifier.
   *
   * @param id the unique identifier of the student.
   * @return a {@link ResponseEntity} containing a paginated list of students wrapped in {@link PageDTO} of {@link GetStudentResponse}
   */
  @GetMapping("/{id}")
  ResponseEntity<GetStudentResponse> getStudentById(@PathVariable Long id) {
    var student = studentService.findById(id);
    return ResponseEntity.ok(mapper.studentToGetStudentResponse(student));
  }

  /**
   * Creates a new student based on the provided request data.
   *
   * @param request an object containing the student details
   * @return a {@link ResponseEntity} with the created student data in {@link CreateStudentResponse}
   */
  @PostMapping
  ResponseEntity<CreateStudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
    var student = studentService.create(mapper.createStudentRequestToStudent(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.studentToCreateStudentResponse(student));
  }

  /**
   * Updates an existing student by its unique identifier.
   *
   * @param request an object containing the updated student details
   * @param id the unique identifier of the student
   * @return
   */
  @PutMapping("/{id}")
  ResponseEntity<Void> updateStudent(@Valid @RequestBody UpdateStudentRequest request, @PathVariable Long id) {
    studentService.updateStudent(mapper.updateStudentRequestToStudent(request), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a student by its unique identifier.
   *
   * @param id the unique identifier of the student to delete
   * @return a {@link ResponseEntity}
   */
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok().build();
  }
}
