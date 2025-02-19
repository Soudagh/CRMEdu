package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.StudentStatus;

/**
 * JPA-entity representing a Student.
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "student", schema = "crmedu")
public class StudentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "patronymic")
  private String patronymic;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "phone", nullable = false)
  private String phone;

  @Column(name = "hex")
  private String hex = "#00FF00";

  @Column(name = "timezone", nullable = false)
  private String timezone = "UTC";

  @Column(name = "birth_date")
  private Date birthDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StudentStatus status = StudentStatus.ACTIVE;

  @Min(1)
  @Max(11)
  @Column(name = "grade", nullable = false)
  private Integer grade;

  @Column(name = "balance", nullable = false)
  private Integer balance = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id")
  private OrganizationEntity organization;
}
