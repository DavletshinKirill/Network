package dev.vorstu.services;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Comment;
import dev.vorstu.db.repositories.CommentRepo;
import dev.vorstu.dto.CommentDTO;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.mappers.CommentMapper;
import dev.vorstu.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        comment.setUser(userService.getLoggedUser());

        comment = commentRepo.save(comment);
        return CommentMapper.INSTANCE.toDto(comment);
    }

    public Page<CommentDTO> getComments(Long id, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return commentRepo.findByPostId(id, pageable).map(CommentMapper.INSTANCE::toDto);
    }

    public ArrayList<CommentDTO> getComments() {

        ArrayList<Comment> comments = StreamSupport.stream(commentRepo.findAll().spliterator(), false).collect(Collectors.toCollection(ArrayList::new));

        return CommentMapper.INSTANCE.listToDTO(comments);
    }
    public ArrayList<CommentDTO> getComments(Long id) {

        ArrayList<Comment> comments = (ArrayList<Comment>) commentRepo.findByPostId(id);

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
        Comment comment = commentRepo.findById(id).get();
        comment.setUser(null);
        comment.setPost(null);
        commentRepo.delete(comment);
    }

    public void deleteComments(ArrayList<CommentDTO> commentDTOS) {
        for (CommentDTO commentDTO : commentDTOS) {
            commentDTO.setUser(null);
            commentDTO.setPost(null);
        }
        ArrayList<Comment> comments = CommentMapper.INSTANCE.listToEntity(commentDTOS);
        commentRepo.deleteAll(comments);
    }
}
