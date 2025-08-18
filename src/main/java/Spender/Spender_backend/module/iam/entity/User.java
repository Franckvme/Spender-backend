package Spender.Spender_backend.module.iam.entity;

import Spender.Spender_backend.shared.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "iam_user")
@Entity
public class User extends AbstractEntity {

  @Id
  @Column(name = "id", updatable = false)
  private UUID id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Builder.Default
  @Column(name = "enabled", nullable = false)
  private boolean enabled = true;

  @ManyToMany
  @JoinTable(name = "iam_user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public User id(UUID id) {
    this.id = id;
    return this;
  }

  public User username(String username) {
    this.username = username;
    return this;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  public boolean isDisabled() {
    return !this.enabled;
  }

  public User addRole(Role role) {
    if (this.roles == null) {
      this.roles = new LinkedHashSet<>();
    }
    this.roles.add(role);
    return this;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User that)) return false;

    return enabled == that.enabled && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(username);
    result = 31 * result + Objects.hashCode(password);
    result = 31 * result + Boolean.hashCode(enabled);
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", enabled=" + enabled +
      '}';
  }
}
