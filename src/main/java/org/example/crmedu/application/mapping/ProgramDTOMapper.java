package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.response.program.GetProgramResponse;
import org.example.crmedu.domain.model.Program;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgramDTOMapper {

  GetProgramResponse programToResponse(Program program);
}
