package dev.vorstu;

import dev.vorstu.db.entities.*;
import dev.vorstu.dto.CommentDTO;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.dto.UserDTO;
import dev.vorstu.mappers.CommentMapper;
import dev.vorstu.mappers.PostMapper;
import dev.vorstu.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
public class TestMapping {

    @Test
    public void postToPostDTO() {
        //given
        Post post = new Post("Morris", "SEDAN", 5);

        //when
        PostDTO postDTO = PostMapper.INSTANCE.toDto(post);

        //then
        assertThat(postDTO).isNotNull();
        assertThat(postDTO.getPhoto()).isEqualTo("Morris");
        assertThat(postDTO.getLikes()).isEqualTo(5);
        assertThat(postDTO.getTitle()).isEqualTo("SEDAN");
    }

    @Test
    public void commentToCommentDTO() {
        //given
        Comment comment = new Comment("Title Comment");
        Post post = new Post("Morris", "SEDAN", 5);
        comment.addPost(post);
        AuthUserEntity user = new AuthUserEntity(true, "user1", "123456", "https://material.angular.io/assets/img/examples/shiba2.jpg", Collections.singleton(new RoleUserEntity("user1", BaseRole.STUDENT)));
        comment.addUser(user);

        //when
        CommentDTO commentDTO = CommentMapper.INSTANCE.toDto(comment);

        //then
        assertThat(commentDTO).isNotNull();
        assertThat(commentDTO.getComment()).isEqualTo("Title Comment");
        assertThat(commentDTO.getPost().getPhoto()).isEqualTo("Morris");
        assertThat(commentDTO.getUser().getUsername()).isEqualTo("user1");
    }

    @Test
    public void userToUserDTO() {
        //given
        AuthUserEntity user = new AuthUserEntity(true, "user1", "123456", "https://material.angular.io/assets/img/examples/shiba2.jpg", Collections.singleton(new RoleUserEntity("user1", BaseRole.STUDENT)));

        //when
        UserDTO userDTO = UserMapper.INSTANCE.toDto(user);

        //then
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo("user1");
        assertThat(userDTO.getMainPhoto()).isEqualTo("https://material.angular.io/assets/img/examples/shiba2.jpg");
    }

    @Test
    public void postDTOToPost() {
        //given
        PostDTO postDTO = new PostDTO(1, "Morris", "SEDAN", 5);

        //when
        Post post = PostMapper.INSTANCE.toEntity(postDTO);

        //then
        assertThat(post).isNotNull();
        assertThat(post.getPhoto()).isEqualTo("Morris");
        assertThat(post.getLikes()).isEqualTo(5);
        assertThat(post.getTitle()).isEqualTo("SEDAN");
    }

    @Test
    public void commentDTOToComment() {
        //given
        CommentDTO commentDTO = new CommentDTO("Title Comment");
        PostDTO postDTO = new PostDTO("Morris", "SEDAN", 5);

        UserDTO user = new UserDTO(1, "user1", "123456");
        commentDTO.setPost(postDTO);
        commentDTO.setUser(user);

        //when
        Comment comment = CommentMapper.INSTANCE.toEntity(commentDTO);

        //then
        assertThat(comment).isNotNull();
        assertThat(comment.getComment()).isEqualTo("Title Comment");
        assertThat(commentDTO.getPost().getPhoto()).isEqualTo("Morris");
        assertThat(commentDTO.getUser().getUsername()).isEqualTo("user1");
    }


}


