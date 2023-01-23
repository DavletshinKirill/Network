package dev.vorstu.db.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.vorstu.db.entities.AuthUserEntity;

@Repository
public interface AuthUserRepo extends CrudRepository<AuthUserEntity, Long> {
  public Set<AuthUserEntity> findAll();

}
