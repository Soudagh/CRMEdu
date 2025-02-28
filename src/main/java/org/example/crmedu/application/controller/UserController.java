package org.example.crmedu.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.application.dto.PageDTO;
import org.example.crmedu.application.dto.request.user.CreateUserRequest;
import org.example.crmedu.application.dto.request.user.UpdateUserRequest;
import org.example.crmedu.application.dto.response.user.CreateUserResponse;
import org.example.crmedu.application.dto.response.user.GetUserResponse;
import org.example.crmedu.application.mapping.UserDTOMapper;
import org.example.crmedu.domain.service.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing users. Provides endpoints to create, retrieve, update and delete users.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

  private final UserService userService;

  private final UserDTOMapper userDTOMapper;

  /**
   * Retrieves a paginates list of users.
   *
   * @param pageable an object specifying pagination parameters (page number and size)
   * @return a {@link ResponseEntity} containing a paginated list of users wrapped in {@link PageDTO} of {@link GetUserResponse}
   */
  @GetMapping
  @Secured({"SUPERUSER", "ORG_ADMIN"})
  public ResponseEntity<PageDTO<GetUserResponse>> getUsers(Pageable pageable) {
    var page = userService.findAll(pageable.getPageNumber(), pageable.getPageSize());
    return ResponseEntity.ok(userDTOMapper.pageUserToPageGetResponse(page));
  }

  /**
   * Retrieves a user by its unique identifier.
   *
   * @param id the unique identifier of the user
   * @return a {@link ResponseEntity} containing the subject data in {@link GetUserResponse}
   */
  @GetMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN"})
  public ResponseEntity<GetUserResponse> getUser(@PathVariable Long id) {
    var user = userService.findById(id);
    return ResponseEntity.ok(userDTOMapper.userToGetResponse(user));
  }

  /**
   * Creates a new user based on the provided request data.
   *
   * @param request an object containing the user details
   * @return a {@link ResponseEntity} with the created subject data in {@link CreateUserResponse}
   */
  @PostMapping
  @Secured({"SUPERUSER", "ORG_ADMIN"})
  public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
    var user = userService.create(userDTOMapper.createUserRequestToUser(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userDTOMapper.userToCreateResponse(user));
  }

  /**
   * Updates an existing user by its unique identifier.
   *
   * @param request an object containing the updated user details
   * @param id the unique identifier of the user to update
   * @return a {@link ResponseEntity}
   */
  @PutMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN"})
  public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequest request, @PathVariable Long id) {
    userService.update(userDTOMapper.updateUserRequestToUser(request), id);
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a user by its unique identifier.
   *
   * @param id the unique identifier of the user to delete
   * @return a {@link ResponseEntity}
   */
  @DeleteMapping("/{id}")
  @Secured({"SUPERUSER", "ORG_ADMIN"})
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.ok().build();
  }
}
