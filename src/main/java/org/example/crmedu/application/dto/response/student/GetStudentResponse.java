package org.example.crmedu.application.dto.response.student;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.application.dto.response.user.GetUserResponse;
import org.example.crmedu.domain.enums.StudentStatus;

/**
 * A DTO representing the response after retrieving a Student.
 */
@Data
@Accessors(chain = true)
public class GetStudentResponse {

  private Long id;

  private GetUserResponse user;

  private String hex;

  private Date birthDate;

  private Integer grade;

  private Integer balance;

  private StudentStatus status;
}
