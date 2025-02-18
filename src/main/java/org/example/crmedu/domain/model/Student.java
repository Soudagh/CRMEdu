package org.example.crmedu.domain.model;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.StudentStatus;

/**
 * A class representing a student entity. This class is used as a domain model.
 */
@Data
@Accessors(chain = true)
public class Student {

  /**
   * The unique identifier of the student.
   */
  private Long id;

  /**
   * Surname of the student.
   */
  private String surname;

  /**
   * Name of the student.
   */
  private String name;

  /**
   * Patronymic of the student. Can be null.
   */
  private String patronymic;

  /**
   * Email of the student. Must be unique.
   */
  private String email;

  /**
   * Phone of the student. Must be unique.
   */
  private String phone;

  /**
   * The time zone of the student. The UTC by default.
   */
  private String timezone;

  /**
   * The color of student's card.
   */
  private String hex;

  /**
   * The birthDate of student.
   */
  private Date birthDate;

  /**
   * The student's grade of education.
   */
  private Integer grade;

  /**
   * Student's balance in current organization.
   */
  private Integer balance;

  /**
   * Student's status of education in organization.
   */
  private StudentStatus status;

  /**
   * The organization, where student study.
   */
  private Organization organization;
}
