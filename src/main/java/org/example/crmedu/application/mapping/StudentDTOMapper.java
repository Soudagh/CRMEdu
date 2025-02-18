package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.student.CreateStudentRequest;
import org.example.crmedu.application.dto.request.student.UpdateStudentRequest;
import org.example.crmedu.application.dto.response.student.CreateStudentResponse;
import org.example.crmedu.application.dto.response.student.GetStudentResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Student;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Student domain model and its corresponding DTOs. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = OrganizationDTOMapper.class)
public interface StudentDTOMapper {

  /**
   * Converts a {@link CreateStudentRequest} DTO to a {@link Student} domain model.
   *
   * @param request the DTO containing student creation details
   * @return the corresponding {@link Student} model
   */
  Student createStudentRequestToStudent(CreateStudentRequest request);

  /**
   * Converts a {@link UpdateStudentRequest} DTO to a {@link Student} domain model.
   *
   * @param request the DTO containing updated student details
   * @return the corresponding {@link Student} model
   */
  Student updateStudentRequestToStudent(UpdateStudentRequest request);

  /**
   * Converts a {@link Student} model to a {@link CreateStudentResponse} DTO.
   *
   * @param student the created student model
   * @return the corresponding {@link CreateStudentResponse} DTO
   */
  CreateStudentResponse studentToCreateStudentResponse(Student student);

  /**
   * Converts a {@link Student} domain model to a {@link GetStudentResponse} DTO.
   *
   * @param student the student model to convert
   * @return the corresponding {@link GetStudentResponse} DTO
   */
  GetStudentResponse studentToGetStudentResponse(Student student);

  /**
   * Converts a paginated {@link Page} of {@link Student} entities to a paginated {@link PageDTO} of {@link GetStudentResponse} DTOs.
   *
   * @param page the paginated student entities
   * @return a paginated response DTO containing student data
   */
  PageDTO<GetStudentResponse> pageStudentToPageDtoGetTutorResponse(Page<Student> page);

}
