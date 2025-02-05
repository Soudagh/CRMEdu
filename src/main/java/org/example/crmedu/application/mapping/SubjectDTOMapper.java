package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.subject.CreateSubjectRequest;
import org.example.crmedu.application.dto.request.subject.UpdateSubjectRequest;
import org.example.crmedu.application.dto.response.subject.CreateSubjectResponse;
import org.example.crmedu.application.dto.response.subject.GetSubjectResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Subject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubjectDTOMapper {

  GetSubjectResponse subjectToGetSubjectResponse(Subject subject);

  PageDTO<GetSubjectResponse> pageSubjectToPageDTO(Page<Subject> subjectPage);

  CreateSubjectResponse subjectToCreateSubjectResponse(Subject subject);

  Subject updateRequestToSubject(UpdateSubjectRequest updateSubjectRequest);

  Subject createRequestToSubject(CreateSubjectRequest createSubjectRequest);
}
