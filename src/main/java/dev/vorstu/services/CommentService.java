package dev.vorstu.services;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Comment;
import dev.vorstu.db.entities.Post;
import dev.vorstu.db.repositories.CommentRepo;
import dev.vorstu.dto.CommentDTO;
import dev.vorstu.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public CommentDTO AddComment(Long id, CommentDTO commentDTO) {
        Post post = postService.GetPostById(id);
        AuthUserEntity user = userService.getUser();
        Comment comment = CommentMapper.INSTANCE.toEntity(commentDTO);
        comment.setPost(post);
        comment.setUser(user);
        return CommentMapper.INSTANCE.toDto(commentRepo.save(comment));
    }

    public ArrayList<CommentDTO> getComments(int id) {

        ArrayList<Comment> comments = StreamSupport.stream(commentRepo.findAll().spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
        comments = comments.stream().filter(el -> el.getPost().getId() == id).collect(Collectors.toCollection(ArrayList::new));

        return CommentMapper.INSTANCE.listToDTO(comments);
    }
}
