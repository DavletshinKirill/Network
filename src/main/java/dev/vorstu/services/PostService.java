package dev.vorstu.services;

import dev.vorstu.db.entities.Post;
import dev.vorstu.db.repositories.PostRepo;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;



    public PostDTO GetPostDTOById(long id) {

        return PostMapper.INSTANCE.toDto(postRepo.findById(id).get());
    }

    public Post GetPostById(long id) {

        return postRepo.findById(id).get();
    }

    private Stream<Post> iterableToStream() {
        return StreamSupport.stream(postRepo.findAll().spliterator(), false);
    }


}
