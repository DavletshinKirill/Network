package dev.vorstu.services;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Comment;
import dev.vorstu.db.repositories.CommentRepo;
import dev.vorstu.dto.CommentDTO;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.mappers.CommentMapper;
import dev.vorstu.mappers.PostMapper;
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

    public CommentDTO addComment(Long id, CommentDTO commentDTO) {
        PostDTO post = postService.getPostDTOById(id);
        AuthUserEntity user = userService.getLoggedUser();
        Comment comment = CommentMapper.INSTANCE.toEntity(commentDTO);
        comment.setPost(PostMapper.INSTANCE.toEntity(post));
        comment.setUser(user);
        comment = commentRepo.save(comment);
        return CommentMapper.INSTANCE.toDto(comment);
    }

    public ArrayList<CommentDTO> getComments(Long id) {

        ArrayList<Comment> comments = StreamSupport.stream(commentRepo.findAll().spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
        comments = comments.stream().filter(el -> el.getPost().getId() == id).collect(Collectors.toCollection(ArrayList::new));

        return CommentMapper.INSTANCE.listToDTO(comments);
    }

    public ArrayList<CommentDTO> getComments() {

        ArrayList<Comment> comments = StreamSupport.stream(commentRepo.findAll().spliterator(), false).collect(Collectors.toCollection(ArrayList::new));

        return CommentMapper.INSTANCE.listToDTO(comments);
    }

    public CommentDTO getComment(long id) {
        return CommentMapper.INSTANCE.toDto(commentRepo.findById(id).get());
    }



    public CommentDTO putComment( CommentDTO commentDTO) {
        Comment comment = commentRepo.findById((long)commentDTO.getId()).get();
        comment.setComment(commentDTO.getComment());
        return CommentMapper.INSTANCE.toDto(commentRepo.save(comment));
    }

    public void deleteComment(long id) {
        commentRepo.deleteById(id);
    }

    public void deleteComments(ArrayList<CommentDTO> commentDTOS) {
        ArrayList<Long> commentsId = commentDTOS.stream().map( u -> (long)u.getId()).collect(Collectors.toCollection(ArrayList::new));
        commentRepo.deleteAllById(commentsId);
    }
}
