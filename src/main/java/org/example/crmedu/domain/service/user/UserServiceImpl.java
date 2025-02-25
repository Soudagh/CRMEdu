package org.example.crmedu.domain.service.user;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.repository.UserRepository;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link UserService} interface. Provides business logic for managing {@link User} entities.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final TutorService tutorService;

  private final OrganizationRepository organizationRepository;

  @Override
  public User create(User user) {
    checkUserConstraints(user);
    validateOrganizationExists(user.getOrganization().getId());
    var createdUser = userRepository.save(user);
    if (createdUser.getRole().equals(Role.TUTOR)) {
      createTutor(createdUser);
    }
    return createdUser;
  }

  @Override
  @Transactional
  public User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(User.class, id));
  }

  @Override
  public Page<User> findAll(int pageNumber, int pageSize) {
    return userRepository.findAll(pageNumber, pageSize);
  }

  @Override
  @Transactional
  public void update(User user, Long id) {
    var userEntity = findById(id);
    checkUserConstraints(user);
    validateOrganizationExists(user.getOrganization().getId());
    userRepository.update(user
        .setId(id)
        .setCreatedAt(userEntity.getCreatedAt())
        .setUpdatedAt(userEntity.getUpdatedAt())
    );
  }

  @Override
  public void delete(Long id) {
    userRepository.delete(id);
  }

  private void checkUserConstraints(User user) {
    checkUserConstraint("email", userRepository::existsByEmail, user);
    checkUserConstraint("phone", userRepository::existsByPhone, user);
  }

  private void checkUserConstraint(String field, Function<User, Boolean> checker, User user) {
    if (checker.apply(user)) {
      throw new EntityExistsException(User.class, field);
    }
  }

  private void validateOrganizationExists(Long organizationId) {
    if (!(organizationRepository.existsById(organizationId))) {
      throw new EntityNotFoundException(Organization.class, organizationId);
    }
  }

  private void createTutor(User user) {
    tutorService.create(new Tutor().setUser(user));
  }
}
