package dev.vorstu.db.entities;

import lombok.Getter;

@Getter
public enum BaseRole {
	SUPER_USER("ROLE_SUPER_USER", "SUPER_USER"),
	STUDENT("ROLE_STUDENT", "STUDENT"),
	TEACHER("ROLE_TEACHER", "TEACHER");
  private final String value;
  private final String role;
  private BaseRole(String value, String role) {
    this.value = value;
    this.role = role;
  }
}
