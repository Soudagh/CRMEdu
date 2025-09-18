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
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.domain.enums.UserStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "verification_token")
  private String verificationToken;

  @Column(name = "phone", nullable = false, unique = true)
  private String phone;

  @Column(name = "timezone", nullable = false)
  private String timezone = "UTC";

  @Column(name = "notifications_enabled", nullable = false)
  private Boolean notificationsEnabled = true;

  @Column(name = "hex", nullable = false)
  private String hex = "#00FF00";

  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private ZonedDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role = Role.USER;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private UserStatus status = UserStatus.PENDING;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id")
  private OrganizationEntity organization;

  @OneToMany(mappedBy = "user")
  private List<NotificationEntity> notifications;

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
