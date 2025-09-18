package org.example.crmedu.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.StudentStatus;

/**
 * Domain model representing a student in the system.
 */
@Data
@Accessors(chain = true)
public class Student {

  /**
   * The unique identifier of the student.
   */
  private Long id;

  private User user;

  /**
   * The color of student's card.
   */
  private String hex;

  /**
   * The birth date of the student.
   */
  private Date birthDate;

  /**
   * The student's grade in school.
   */
  private Integer grade;

  /**
   * The current balance of the student.
   */
  private Integer balance;

  /**
   * The current education status of the student.
   */
  private StudentStatus status;

  private List<Subscription> subscriptions = new ArrayList<>();
}
