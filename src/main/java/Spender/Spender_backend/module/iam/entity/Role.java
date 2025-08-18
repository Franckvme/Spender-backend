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
@Table(name = "iam_role")
@Entity
public class Role extends AbstractEntity {

  @Id
  @Column(name = "id", updatable = false)
  private UUID id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description", length = 500)
  private String description;

  @Builder.Default
  @Column(name = "enabled", nullable = false)
  private boolean enabled = true;

  @ManyToMany
  @JoinTable(name = "iam_role_permission",
    joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;

  public void addPermission(Permission permission) {
    if (this.permissions == null) {
      this.permissions = new LinkedHashSet<>();
    }
    this.permissions.add(permission);
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Role that)) return false;

    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(id);
    result = 31 * result + Objects.hashCode(name);
    result = 31 * result + Objects.hashCode(description);
    return result;
  }
}
