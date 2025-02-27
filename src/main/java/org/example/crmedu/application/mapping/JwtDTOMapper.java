package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.response.auth.JwtResponse;
import org.example.crmedu.domain.model.Jwt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtDTOMapper {

  JwtResponse toJwtResponse(Jwt jwt);
}
