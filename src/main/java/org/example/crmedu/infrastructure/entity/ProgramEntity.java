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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.ProgramStatus;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "program", schema = "crmedu")
public class ProgramEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id")
  private OrganizationEntity organization;

  @Column(name = "lesson_count", nullable = false)
  private Integer lessonCount;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "program_status", nullable = false)
  private ProgramStatus programStatus = ProgramStatus.ACTIVE;

  @OneToMany(mappedBy = "program")
  private List<SubjectProgramEntity> subjectProgram;
}
