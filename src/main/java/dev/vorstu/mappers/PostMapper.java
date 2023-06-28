package dev.vorstu.mappers;

import dev.vorstu.db.entities.Post;
import dev.vorstu.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper( PostMapper.class );

    PostDTO toDto(Post post);

    Post toEntity(PostDTO post);

    ArrayList<PostDTO> listToDTO(ArrayList<Post> listOfPosts);
}
