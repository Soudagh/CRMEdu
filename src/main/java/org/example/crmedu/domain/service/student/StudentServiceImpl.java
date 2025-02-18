package org.example.crmedu.domain.service.student;

import lombok.RequiredArgsConstructor;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.example.crmedu.domain.model.Page;
import org.example.crmedu.domain.model.Student;
import org.example.crmedu.domain.repository.StudentRepository;
import org.example.crmedu.domain.service.organization.OrganizationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  private final OrganizationService organizationService;


  @Override
  public Student create(Student student) {
    organizationService.checkExistanceById(student.getOrganization().getId());
    checkStudentConstraints(student);
    return studentRepository.save(student);
  }

  @Override
  public Student findById(Long id) {
    return getStudentByIdOrThrow(id);
  }

  @Override
  public Page<Student> getStudents(int pageNumber, int pageSize) {
    return studentRepository.findAll(pageNumber, pageSize);
  }

  @Override
  public void updateStudent(Student student, Long id) {
    var studentEntity = getStudentByIdOrThrow(id);
    student.setOrganization(studentEntity.getOrganization());
    checkStudentConstraints(student);
    studentRepository.update(
        student.setId(id)
    );
  }

  @Override
  public void deleteStudent(Long id) {
    studentRepository.delete(id);
  }

  private Student getStudentByIdOrThrow(Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Student.class, id));
  }

  private void checkStudentConstraints(Student student) {
    if (studentRepository.existsByEmailAndPhoneInOrganization(student)) {
      throw new EntityExistsException(Student.class, "email and phone");
    }
  }

}
