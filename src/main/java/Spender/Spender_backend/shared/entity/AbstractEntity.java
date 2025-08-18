package Spender.Spender_backend.shared.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class AbstractEntity {

  @CreationTimestamp
  @Column(name = "created_at")
  protected Instant createdAt;

  @Column(name = "updated_at")
  protected Instant updatedAt;
}
