package org.example.crmedu.domain.service.user;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} interface. Provides business logic for managing {@link User} entities.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final OrganizationRepository organizationRepository;

  @Override
  public User create(User user) {
    checkUserConstraints(user);
    if (organizationRepository.existsById(user.getOrganization().getId())) {
      return userRepository.save(user);
    }
    throw new EntityNotFoundException(Organization.class, user.getOrganization().getId());
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(User.class, id));
  }

  @Override
  public Page<User> findAll(int pageNumber, int pageSize) {
    return userRepository.findAll(pageNumber, pageSize);
  }

  @Override
  public void update(User user, Long id) {
    var userEntity = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(User.class, id));
    checkUserConstraints(user);
    if (isOrganizationExists(user.getOrganization())) {
      userRepository.update(user
          .setId(id)
          .setCreatedAt(userEntity.getCreatedAt())
          .setUpdatedAt(userEntity.getUpdatedAt())
      );
    }
    throw new EntityNotFoundException(User.class, id);
  }

  private void checkUserConstraints(User user) {
    if (userRepository.existsByEmail(user)) {
      throw new EntityExistsException(User.class, "email");
    }
    if (userRepository.existsByPhone(user)) {
      throw new EntityExistsException(User.class, "phone");
    }
  }

  private boolean isOrganizationExists(Organization organization) {
    if (organization == null) {
      return true;
    }
    return organizationRepository.existsById(organization.getId());
  }

  @Override
  public void delete(Long id) {
    userRepository.delete(id);
  }
}
