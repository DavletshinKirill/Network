package dev.vorstu.db.repositories;

import dev.vorstu.db.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepo extends CrudRepository<Comment, Long> {
    public Page<Comment> findCommentsByPostId(Long postId, Pageable pageable);
    public List<Comment> findByPostId(Long postId);

}
