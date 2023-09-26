package dev.vorstu.services;

import dev.vorstu.db.entities.Post;
import dev.vorstu.db.repositories.PostRepo;
import dev.vorstu.dto.CommentDTO;
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


    //@Cacheable(value = "PostService::getPostDTOById", key = "#id")
    public PostDTO getPostDTOById(long id) {

        return PostMapper.INSTANCE.toDto(postRepo.findById(id).get());
    }

    public PostDTO addPost(PostDTO postDTO) {
        return PostMapper.INSTANCE.toDto(postRepo.save(PostMapper.INSTANCE.toEntity(postDTO)));
    }
//    @Caching(put = {
//            @CachePut(value = "PostService::getPostDTOById",
//                    key = "#postDTO.id"),
//    })
    public PostDTO updatePost(PostDTO postDTO) {
        Post post = postRepo.findById((long)postDTO.getId()).get();
        post.setTitle(postDTO.getTitle());
        post.setPhoto(postDTO.getPhoto());
        return PostMapper.INSTANCE.toDto(postRepo.save(post));
    }
    //@CacheEvict(value = "PostService::getPostDTOById", key = "#id")
    public void deletePost(Long id) {
        ArrayList<CommentDTO> commentDTOS = commentService.getComments(id);
        commentService.deleteComments(commentDTOS);
        postRepo.deleteById(id);
    }

    public ArrayList<PostDTO> getPosts() {
        ArrayList<Post> posts = StreamSupport.stream(postRepo.findAll().spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
        return PostMapper.INSTANCE.listToDTO(posts);
    }
}
