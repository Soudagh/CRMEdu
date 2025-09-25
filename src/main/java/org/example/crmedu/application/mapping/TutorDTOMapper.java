package org.example.crmedu.application.mapping;

import java.util.Set;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.tutor.CreateTutorRequest;
import org.example.crmedu.application.dto.request.tutor.PatchTutorGradesRequest;
import org.example.crmedu.application.dto.request.tutor.PatchTutorSubjectsRequest;
import org.example.crmedu.application.dto.request.tutor.UpdateTutorRequest;
import org.example.crmedu.application.dto.response.tutor.CreateTutorResponse;
import org.example.crmedu.application.dto.response.tutor.GetTutorResponse;
import org.example.crmedu.application.dto.response.tutor.GetTutorSimpleResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.example.crmedu.domain.model.Tutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * A mapper interface for converting between Tutor domain model and its corresponding DTOs. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = {UserDTOMapper.class, SubjectDTOMapper.class})
public interface TutorDTOMapper {

  /**
   * Converts a paginated {@link Page} of {@link Tutor} entities to a paginated {@link PageDTO} of {@link GetTutorResponse} DTOs.
   *
   * @param page the paginated tutor entities
   * @return a paginated response DTO containing tutor data
   */
  PageDTO<GetTutorResponse> pageTutorToPageGetResponse(Page<Tutor> page);

  /**
   * Converts a {@link CreateTutorRequest} DTO to a {@link Tutor} model.
   *
   * @param request the DTO containing tutor creation details
   * @return the corresponding {@link Tutor} model
   */
  Tutor createRequestToTutor(CreateTutorRequest request);

  /**
   * Converts a {@link Tutor} model to a {@link CreateTutorResponse} DTO.
   *
   * @param tutor the creawted tutor model
   * @return the corresponding {@link CreateTutorResponse} DTO
   */
  CreateTutorResponse tutorToCreateResponse(Tutor tutor);

  /**
   * Converts a {@link Tutor} domain model to a {@link GetTutorResponse} DTO.
   *
   * @param tutor the tutor model to convert
   * @return the corresponding {@link GetTutorResponse} DTO
   */
  GetTutorResponse tutorToGetResponse(Tutor tutor);

  GetTutorSimpleResponse tutorToGetSimpleResponse(Tutor tutor);

  /**
   * Converts an {@link UpdateTutorRequest} DTO to a {@link Tutor} model.
   *
   * @param request the DTO containing updated tutor details
   * @return the corresponding {@link Tutor} model
   */
  Tutor updateRequestToUser(UpdateTutorRequest request);

  /**
   * Converts a {@link Tutor} to its unique identifier
   *
   * @param tutor the tutor model to convert
   * @return the corresponding unique identifier
   */
  @Named("tutorToId")
  default Long tutorToId(Tutor tutor) {
    return tutor.getId();
  }

  /**
   * Converts a {@link PatchTutorSubjectsRequest} to set of grades.
   *
   * @param request the DTO containing details about grades
   * @return the corresponding {@link Set<Integer>}
   */
  @Mapping(target = ".", source = "request.grades")
  default Set<Integer> patchTutorsGradesRequestToGradesSet(PatchTutorGradesRequest request) {
    return request.getGrades();
  }

  /**
   * Converts a {@link PatchTutorSubjectsRequest} to set of subjects.
   *
   * @param request the DTO containing set of subject's ids
   * @param subjectMapper mapper that helps convert subject ids to subject models
   * @return the corresponding {@link Set<Subject>}
   */
  default Set<Subject> patchTutorsSubjectRequestToSubjectSet(PatchTutorSubjectsRequest request, SubjectDTOMapper subjectMapper) {
    return subjectMapper.setIdsToSetSubjects(request.getSubjects());
  }
}
