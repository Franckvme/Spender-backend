package Spender.Spender_backend.module.iam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "iam_permission")
@Entity
public class Permission {

  @Id
  @Column(name = "id", updatable = false)
  private UUID id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description", length = 500)
  private String description;

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Permission that)) return false;

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
