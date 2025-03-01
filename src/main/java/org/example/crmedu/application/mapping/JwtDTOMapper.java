package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.response.auth.JwtResponse;
import org.example.crmedu.domain.model.Jwt;
import org.mapstruct.Mapper;

/**
 * A mapper interface for converting between {@code Jwt} domain model and its corresponding DTOs. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface JwtDTOMapper {

  /**
   * Converts a {@link Jwt} entity to a {@link JwtResponse}.
   *
   * @param jwt the jwt model to convert
   * @return the corresponding {@link JwtResponse} DTO
   */
  JwtResponse toJwtResponse(Jwt jwt);
}
