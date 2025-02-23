package org.example.crmedu.infrastructure.repository.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.UserRepository;
import org.example.crmedu.infrastructure.mapping.UserEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link UserRepository} interface using JPA. Provides methods for managing {@link User} entities in the database.
 */
@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final DataUserRepository userRepository;

  private final UserEntityMapper userMapper;

  @Override
  public Page<User> findAll(int pageNumber, int pageSize) {
    var page = userRepository.findAll(
            PageRequest.of(
                pageNumber,
                pageSize
            ))
        .map(userMapper::userEntityToUser);
    return new Page<User>()
        .setContent(page.getContent())
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalPages(page.getTotalPages())
        .setTotalCount(page.getTotalElements());
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id).map(userMapper::userEntityToUser);
  }

  @Override
  public boolean existsByEmail(User user) {
    return userRepository.existsByEmailAndIdIsNot(user.getEmail(), user.getId());
  }

  @Override
  public boolean existsByPhone(User user) {
    return userRepository.existsByPhoneAndIdIsNot(user.getPhone(), user.getId());
  }


  @Override
  public User save(User user) {
    var requestedUserEntity = userMapper.userToUserEntity(user);
    var responsedUserEntity = userRepository.save(requestedUserEntity);
    return userMapper.userEntityToUser(responsedUserEntity);
  }

  @Override
  public void update(User user) {
    userRepository.save(userMapper.userToUserEntity(user));
  }

  @Override
  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
