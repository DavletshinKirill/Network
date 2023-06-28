package dev.vorstu.services;


import dev.vorstu.dto.PostDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("/test-application.yaml")
@Sql( value = {"/scripts/DropTables.sql", "/scripts/CreateTables.sql",
               "/scripts/AddForeignKey.sql", "/scripts/insert/InsertUsers.sql",
               "/scripts/insert/InsertPosts.sql", "/scripts/insert/InsertComments.sql"},
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TestPostService {

    @Autowired
    private PostService postService;


    @Test
    public void addPost(){
        PostDTO postDTO = new PostDTO(4, "Morris", "SEDAN", 5);
        postService.addPost(postDTO);

        assertThat(postService.getPostDTOById(4).getTitle()).isEqualTo(postDTO.getTitle());
    }

    @Test
    public void getPostDTOById(){
        PostDTO postDTO = new PostDTO(1, "Morris", "SEDAN", 5);
        postService.addPost(postDTO);
        PostDTO newPostDTO = postService.getPostDTOById(postDTO.getId());
        assertThat(postDTO.getTitle()).isEqualTo(newPostDTO.getTitle());
    }

    @Test
    public void deletePost() {
        PostDTO postDTO = postService.getPostDTOById(1);
        int sizeBefore = postService.getPosts().size();
        postService.deletePost((long)postDTO.getId());
        int sizeAfter = postService.getPosts().size();
        assertThat(sizeBefore).isNotEqualTo(sizeAfter);
        assertThat(sizeBefore - 1).isEqualTo(sizeAfter);
    }

    @Test
    public void updatePost(){
        PostDTO postDTO = postService.getPostDTOById(1);
        postDTO.setLikes(666);
        postDTO.setTitle("Change title in Test");
        postService.updatePost(postDTO);
        assertThat(postService.getPostDTOById(1).getLikes()).isEqualTo(postDTO.getLikes());
    }

}
