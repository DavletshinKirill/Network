package dev.vorstu.mappers;

import dev.vorstu.db.entities.Comment;
import dev.vorstu.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper( CommentMapper.class );

    CommentDTO toDto(Comment post);

    Comment toEntity(CommentDTO post);

    ArrayList<CommentDTO> listToDTO(ArrayList<Comment> listOfComments);
}
