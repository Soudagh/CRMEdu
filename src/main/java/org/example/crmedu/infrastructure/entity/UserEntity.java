package org.example.crmedu.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

/**
 * JPA-repository representing an User
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "user", schema = "crmedu")
public class UserEntity {

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

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "timezone", nullable = false)
  private String timezone;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private ZonedDateTime updatedAt;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "roles", columnDefinition = "jsonb")
  private Set<Role> roles = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id")
  private OrganizationEntity organization;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserEntity other)) {
      return false;
    }
    return id != null && this.id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
