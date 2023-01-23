package dev.vorstu.db.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Posts;

public interface PostRepo extends CrudRepository<Posts, Long> {

}
