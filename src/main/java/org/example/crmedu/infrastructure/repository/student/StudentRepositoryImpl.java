package org.example.crmedu.infrastructure.repository.student;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.repository.StudentRepository;
import org.example.crmedu.infrastructure.mapping.StudentEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link StudentRepository } interface. Provides methods for managing {@link Student } entities in the database.
 */
@Component
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

  private final StudentEntityMapper mapper;

  private final DataStudentRepository studentRepository;

  @Override
  public Student save(Student student) {
    var requestedEntity = mapper.studentToStudentEntity(student);
    var responsedEntity = studentRepository.save(requestedEntity);
    return mapper.studentEntityToStudent(responsedEntity);
  }

  @Override
  public Optional<Student> findById(Long id) {
    return studentRepository.findById(id).map(mapper::studentEntityToStudent);
  }

  @Override
  public boolean existsByEmailAndPhoneInOrganization(Student student) {
    return studentRepository.existsByEmailAndPhoneAndOrganization_Id(
        student.getEmail(),
        student.getPhone(),
        student.getOrganization().getId()
    );
  }

  @Override
  public Page<Student> findAll(int pageNumber, int pageSize) {
    var page = studentRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(mapper::studentEntityToStudent);
    return new Page<Student>()
        .setContent(page.getContent())
        .setPage(pageNumber)
        .setLimit(pageSize)
        .setTotalPages(page.getTotalPages())
        .setTotalCount(page.getTotalElements());
  }

  @Override
  public void update(Student student) {
    studentRepository.save(mapper.studentToStudentEntity(student));
  }

  @Override
  public void delete(Long id) {
    studentRepository.deleteById(id);
  }
}
