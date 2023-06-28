package dev.vorstu.services;

import dev.vorstu.db.entities.Post;
import dev.vorstu.db.repositories.PostRepo;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentService commentService;



    public PostDTO getPostDTOById(long id) {

        return PostMapper.INSTANCE.toDto(postRepo.findById(id).get());
    }

    public PostDTO addPost(PostDTO postDTO) {
        return PostMapper.INSTANCE.toDto(postRepo.save(PostMapper.INSTANCE.toEntity(postDTO)));
    }
    public PostDTO updatePost(PostDTO postDTO) {
        return PostMapper.INSTANCE.toDto(postRepo.save(PostMapper.INSTANCE.toEntity(postDTO)));
    }
    public void deletePost(Long id) {
        commentService.deleteComments(commentService.getComments(id));
    }

    public ArrayList<PostDTO> getPosts() {
        ArrayList<Post> posts = StreamSupport.stream(postRepo.findAll().spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
        return PostMapper.INSTANCE.listToDTO(posts);
    }

    public ArrayList<PostDTO> getPostsById(Long id) {
        ArrayList<Post> posts = StreamSupport.stream(postRepo.findAll().spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
        return PostMapper.INSTANCE.listToDTO(posts);
    }

}
