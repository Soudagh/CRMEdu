package org.example.crmedu.domain.service.user;

import java.util.ArrayList;
import java.util.List;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.enums.UserStatus;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.exception.UniqueConstraintError;
import org.example.crmedu.domain.exception.UniqueConstraintsException;
import org.example.crmedu.domain.model.Lesson;
import org.example.crmedu.domain.model.Organization;
import org.example.crmedu.domain.model.Tutor;
import org.example.crmedu.domain.model.User;
import org.example.crmedu.domain.repository.OrganizationRepository;
import org.example.crmedu.domain.repository.UserRepository;
import org.example.crmedu.domain.service.BaseService;
import org.example.crmedu.domain.service.jwt.JwtService;
import org.example.crmedu.domain.service.jwt.PasswordEncode;
import org.example.crmedu.domain.service.student.StudentService;
import org.example.crmedu.domain.service.tutor.TutorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link UserService} interface. Provides business logic for managing {@link User} entities.
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

  private final UserRepository userRepository;

  private final TutorService tutorService;

  private final StudentService studentService;

  private final OrganizationRepository organizationRepository;

  private final PasswordEncode passwordEncode;


  public UserServiceImpl(
      UserRepository userRepository,
      TutorService tutorService, StudentService studentService,
      OrganizationRepository organizationRepository,
      PasswordEncode passwordEncode) {
    super(userRepository, User.class);
    this.userRepository = userRepository;
    this.tutorService = tutorService;
    this.studentService = studentService;
    this.organizationRepository = organizationRepository;
    this.passwordEncode = passwordEncode;
  }


  @Override
  public User create(User user) {
    checkUserConstraints(user);
    validateOrganizationExists(user.getOrganization().getId());
    user.setPassword(passwordEncode.encode(user.getPassword())).setStatus(UserStatus.PENDING);
    var createdUser = userRepository.create(user);
    if (createdUser.getRole().equals(Role.TUTOR)) {
      createTutor(createdUser);
    }
    return createdUser;
  }

  @Override
  @Transactional
  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException(User.class, email));
  }

  @Override
  @Transactional
  public User findByVerificationToken(String token) {
    return userRepository.findByVerificationToken(token)
        .orElseThrow(() -> new EntityNotFoundException(User.class, token));
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
  @Transactional
  public void verifyUserByVerificationToken(String token) {
    var user = findByVerificationToken(token);
    user.setStatus(UserStatus.ACTIVE);
    userRepository.update(user);
  }

  @Override
  @Transactional
  public List<Lesson> getUserSchedule(User user) {
    switch (user.getRole()) {
      case Role.TUTOR -> {
        return tutorService.getScheduleByUserId(user.getId());
      }
      case Role.STUDENT -> {
        return studentService.getScheduleByUserId(user.getId());
      }
    }
    return List.of();
  }

  @Override
  public void updateNotificationsMode(User user, Boolean newMode) {
    user.setNotificationsEnabled(newMode);
    userRepository.update(user);
  }

  private void checkUserConstraints(User user) {
    var violations = new ArrayList<UniqueConstraintError>();
    if (userRepository.existsByEmail(user)) {
      violations.add(new UniqueConstraintError("email", user.getEmail()));
    }
    if (userRepository.existsByPhone(user)) {
      violations.add(new UniqueConstraintError("phone", user.getPhone()));
    }
    if (!violations.isEmpty()) {
      throw new UniqueConstraintsException(User.class, violations);
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
