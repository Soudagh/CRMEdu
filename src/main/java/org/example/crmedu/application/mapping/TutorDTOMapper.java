package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.tutor.CreateTutorRequest;
import org.example.crmedu.application.dto.request.tutor.UpdateTutorRequest;
import org.example.crmedu.application.dto.response.tutor.CreateTutorResponse;
import org.example.crmedu.application.dto.response.tutor.GetTutorResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Tutor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserDTOMapper.class)
public interface TutorDTOMapper {

  PageDTO<GetTutorResponse> pageTutorToPageGetResponse(Page<Tutor> page);

  Tutor createRequestToTutor(CreateTutorRequest request);

  CreateTutorResponse tutorToCreateResponse(Tutor tutor);

  GetTutorResponse tutorToGetResponse(Tutor tutor);

  Tutor updateRequestToUser(UpdateTutorRequest request);
}
