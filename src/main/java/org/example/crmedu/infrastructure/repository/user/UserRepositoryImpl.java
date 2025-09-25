package org.example.crmedu.infrastructure.repository.user;

import java.util.Optional;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.UserRepository;
import org.example.crmedu.infrastructure.entity.UserEntity;
import org.example.crmedu.infrastructure.mapping.UserEntityMapper;
import org.example.crmedu.infrastructure.repository.BaseRepository;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link UserRepository} interface using JPA. Provides methods for managing {@link User} entities in the database.
 */
@Component
public class UserRepositoryImpl extends BaseRepository<User, UserEntity, Long> implements UserRepository {

  private final DataUserRepository userRepository;

  private final UserEntityMapper userMapper;

  public UserRepositoryImpl(
      DataUserRepository userRepository,
      UserEntityMapper userMapper
  ) {
    super(userRepository, userMapper);
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email).map(userMapper::toDomain);
  }

  @Override
  public Optional<User> findByVerificationToken(String token) {
    return userRepository.findByVerificationToken(token).map(userMapper::toDomain);
  }

  @Override
  public boolean existsByEmail(User user) {
    return userRepository.existsByEmailAndIdIsNot(user.getEmail(), user.getId());
  }

  @Override
  public boolean existsByPhone(User user) {
    return userRepository.existsByPhoneAndIdIsNot(user.getPhone(), user.getId());
  }

}
