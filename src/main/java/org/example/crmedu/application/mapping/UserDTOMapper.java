package org.example.crmedu.application.mapping;

import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.user.CreateUserRequest;
import org.example.crmedu.application.dto.request.user.UpdateUserRequest;
import org.example.crmedu.application.dto.response.user.CreateUserResponse;
import org.example.crmedu.application.dto.response.user.GetUserResponse;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.User;
import org.mapstruct.Mapper;

/**
 *
 */
@Mapper(componentModel = "spring", uses = OrganizationDTOMapper.class)
public interface UserDTOMapper {

  PageDTO<GetUserResponse> pageUserToPageGetResponse(Page<User> page);

  GetUserResponse userToGetResponse(User user);

  User createUserRequestToUser(CreateUserRequest request);

  CreateUserResponse userToCreateResponse(User user);

  User updateUserRequestToUser(UpdateUserRequest request);
}
