package dev.vorstu.db.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserEntity extends BaseEntity {
  private String username;
  @Enumerated(EnumType.STRING)
  private BaseRole role;
}
