package dev.vorstu.db.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.vorstu.db.entities.Comment;

public interface CommentRepo extends CrudRepository<Comment, Long> {

}
