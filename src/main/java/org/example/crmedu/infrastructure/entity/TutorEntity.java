package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.model.Link;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * JPA-entity representing a Tutor.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "tutor", schema = "crmedu")
public class TutorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Column(name = "social_contacts", columnDefinition = "jsonb", nullable = false)
  @JdbcTypeCode(SqlTypes.JSON)
  private List<Link> links;

  @Column(name = "notes")
  private String notes;

  @ManyToMany
  @JoinTable(
      name = "tutors_subjects",
      schema = "crmedu",
      joinColumns = @JoinColumn(name = "tutor_id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id")
  )
  private Set<SubjectEntity> subjects = new HashSet<>();

  @ElementCollection
  @CollectionTable(name = "tutor_grades", joinColumns = @JoinColumn(name = "tutor_id"))
  @Column(name = "grade")
  private Set<Integer> grades = new HashSet<>();

  @OneToMany(mappedBy = "tutor")
  private Set<TutorScheduleEntity> schedule = new HashSet<>();

  @OneToMany(mappedBy = "tutor")
  private List<LessonEntity> lessons;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SubjectEntity other)) {
      return false;
    }
    return id != null && id.equals(other.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
