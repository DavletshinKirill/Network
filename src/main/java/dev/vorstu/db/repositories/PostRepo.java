package dev.vorstu.db.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.vorstu.db.entities.Post;

public interface PostRepo extends CrudRepository<Post, Long> {

}
