package dev.vorstu.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.dto.UserDTO;
import dev.vorstu.services.PostService;
import dev.vorstu.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application.yaml")
@Sql( value = {"/scripts/DropTables.sql", "/scripts/CreateTables.sql",
        "/scripts/AddForeignKey.sql", "/scripts/insert/InsertUsers.sql",
        "/scripts/insert/InsertPosts.sql", "/scripts/insert/InsertComments.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@WithMockUser("admin")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addPost() throws Exception {
        PostDTO postDTO = new PostDTO("test photo", "test title", 55);

        mockMvc.perform(post("/api/home/user/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likes").value(postDTO.getLikes()))
                .andExpect(jsonPath("$.photo").value(postDTO.getPhoto()))
                .andExpect(jsonPath("$.title").value(postDTO.getTitle()));
    }

//    @Test
//    void updatePost() throws Exception {
//        Long id = 1L;
//        PostDTO postDTO = postService.getPostDTOById(id);
//        postDTO.setTitle("new test");
//        postDTO.setLikes(666);
//        postDTO.setPhoto("new photo test");
//
//        mockMvc.perform(put("/api/home/user/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(postDTO))
//                        .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.likes").value(postDTO.getLikes()))
//                .andExpect(jsonPath("$.photo").value(postDTO.getPhoto()))
//                .andExpect(jsonPath("$.title").value(postDTO.getTitle()));
//    }

//    @Test
//    void deletePost() throws Exception {
//        int sizeBefore = postService.getPosts().size();
//        Long id = 1L;
//        mockMvc.perform(delete("/api/home/user/{id}", id))
//                .andExpect(status().isOk());
//        int sizeAfter = postService.getPosts().size();
//
//        assertThat(sizeBefore).isNotEqualTo(sizeAfter);
//        assertThat(sizeBefore - 1).isEqualTo(sizeAfter);
//    }

    @Test
    void getPost() throws Exception {
        Long id = 1L;
        UserDTO userDTO = userService.getUserById(id);
        mockMvc.perform(get("/api/home/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId()))
                .andExpect(jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(jsonPath("$.mainPhoto").value(userDTO.getMainPhoto()));
    }
}