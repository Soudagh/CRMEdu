package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.auth.SignInRequest;
import org.example.crmedu.application.dto.request.auth.SignUpRequest;
import org.example.crmedu.application.dto.request.user.CreateUserRequest;
import org.example.crmedu.application.dto.request.user.UpdateUserRequest;
import org.example.crmedu.application.dto.response.auth.SignUpResponse;
import org.example.crmedu.application.dto.response.user.CreateUserResponse;
import org.example.crmedu.application.dto.response.user.GetUserResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper interface for converting between user domain model and its corresponding DTOs. Uses MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring", uses = OrganizationDTOMapper.class)
public interface UserDTOMapper {

  /**
   * Converts a paginated {@link Page} of {@link User} entities to a paginated {@link PageDTO} of {@link GetUserResponse} DTOs.
   *
   * @param page the paginated user entities
   * @return a paginated response DTO containing user data
   */
  PageDTO<GetUserResponse> pageUserToPageGetResponse(Page<User> page);

  /**
   * Converts a {@link User} domain model to a {@link GetUserResponse} DTO.
   *
   * @param user the user model to convert
   * @return the corresponding {@link GetUserResponse} DTO
   */
  @Mapping(target = "organization", source = "user.organization.id")
  GetUserResponse userToGetResponse(User user);

  /**
   * Converts a {@link CreateUserRequest} DTO to a {@link User} model.
   *
   * @param request the DTO containing user creation details
   * @return the corresponding {@link User} model
   */
  User createUserRequestToUser(CreateUserRequest request);

  /**
   * Converts a {@link User} model to a {@link CreateUserResponse} DTO.
   *
   * @param user the created user model
   * @return the corresponding {@link CreateUserResponse} DTO
   */
  @Mapping(target = "organization", source = "user.organization.id")
  CreateUserResponse userToCreateResponse(User user);

  /**
   * Converts an {@link UpdateUserRequest} DTO to a {@link User} model.
   *
   * @param request the DTO containing updated user details
   * @return the corresponding {@link User} model
   */
  User updateUserRequestToUser(UpdateUserRequest request);

  /**
   * Converts the unique identifier of the user to {@link User} model.
   *
   * @param id the unique identifier of user
   * @return the corresponding {@link User} model
   */
  User idToUser(Long id);

  /**
   * Converts a {@link SignInRequest} DTO to a {@link User} model
   *
   * @param signInRequest the DTO containing data for log in
   * @return the corresponding {@link User} model
   */
  User singInRequestToUser(SignInRequest signInRequest);

  /**
   * Converts a {@link SignUpRequest} DTO to a {@link User} model
   *
   * @param signUpRequest the DTO containing data for signing up
   * @return the corresponding {@link User} model
   */
  User singUpRequestToUser(SignUpRequest signUpRequest);

  /**
   * Converts a {@link User} model to a {@link SignUpResponse} DTO.
   *
   * @param user the user that signed up
   * @return the corresponding {@link SignUpResponse} DTO
   */
  SignUpResponse userToSignUpResponse(User user);
}
