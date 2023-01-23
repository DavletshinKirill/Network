package dev.vorstu.db.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Comments;

public interface CommentRepo extends CrudRepository<Comments, Long> {

}
