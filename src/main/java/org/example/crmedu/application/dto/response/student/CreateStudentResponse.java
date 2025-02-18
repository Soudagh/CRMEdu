package org.example.crmedu.application.dto.response.student;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.StudentStatus;

/**
 * A DTO representing the response after creating a Student.
 */
@Data
@Accessors(chain = true)
public class CreateStudentResponse {

  private Long id;

  private String surname;

  private String name;

  private String patronymic;

  private String email;

  private String phone;

  private String timezone;

  private String hex;

  private Date birthDate;

  private Integer grade;

  private Integer balance;

  private StudentStatus status;

}
