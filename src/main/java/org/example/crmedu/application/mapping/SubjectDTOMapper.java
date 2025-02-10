package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.subject.CreateSubjectRequest;
import org.example.crmedu.application.dto.request.subject.UpdateSubjectRequest;
import org.example.crmedu.application.dto.response.subject.CreateSubjectResponse;
import org.example.crmedu.application.dto.response.subject.GetSubjectResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between Subject domain model and its corresponding DTOs. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = OrganizationDTOMapper.class)
public interface SubjectDTOMapper {

  /**
   * Converts a {@link Subject} domain model to a {@link GetSubjectResponse} DTO.
   *
   * @param subject the subject model to convert
   * @return the corresponding {@link GetSubjectResponse} DTO
   */
  GetSubjectResponse subjectToGetSubjectResponse(Subject subject);

  /**
   * Converts a paginated {@link Page} of {@link Subject} entities to a paginated {@link PageDTO} of {@link GetSubjectResponse} DTOs.
   *
   * @param subjectPage the paginated subject entities
   * @return a paginated response DTO containing subject data
   */
  PageDTO<GetSubjectResponse> pageSubjectToPageDTO(Page<Subject> subjectPage);

  /**
   * Converts a {@link Subject} model to a {@link CreateSubjectResponse} DTO.
   *
   * @param subject the created subject model
   * @return the corresponding {@link CreateSubjectResponse} DTO
   */
  CreateSubjectResponse subjectToCreateSubjectResponse(Subject subject);

  /**
   * Converts an {@link UpdateSubjectRequest} DTO to a {@link Subject} model.
   *
   * @param request the DTO containing updated subject details
   * @return the corresponding {@link Subject} model
   */
  Subject updateRequestToSubject(UpdateSubjectRequest request);

  /**
   * Converts a {@link CreateSubjectRequest} DTO to a {@link Subject} model.
   *
   * @param request the DTO containing subject creation details
   * @return the corresponding {@link Subject} model
   */
  Subject createRequestToSubject(CreateSubjectRequest request);
}
