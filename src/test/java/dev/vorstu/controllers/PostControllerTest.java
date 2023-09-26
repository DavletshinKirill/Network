package dev.vorstu.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vorstu.dto.CommentDTO;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.services.CommentService;
import dev.vorstu.services.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@TestPropertySource("/application.yaml")
@Sql( value = {"/scripts/DropTables.sql", "/scripts/CreateTables.sql",
        "/scripts/AddForeignKey.sql", "/scripts/insert/InsertUsers.sql",
        "/scripts/insert/InsertPosts.sql", "/scripts/insert/InsertComments.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@WithMockUser("admin")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPost() throws Exception {
        Long id = 1L;
        PostDTO postDTO = postService.getPostDTOById(id);
        mockMvc.perform(get("/api/home/post/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postDTO.getId()))
                .andExpect(jsonPath("$.likes").value(postDTO.getLikes()))
                .andExpect(jsonPath("$.photo").value(postDTO.getPhoto()))
                .andExpect(jsonPath("$.title").value(postDTO.getTitle()));
    }

//    @Test
//    void addComment() throws Exception {
//        CommentDTO commentDTO = new CommentDTO("test comment");
//
//        mockMvc.perform(post("/api/home/post/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(commentDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.comment").value(commentDTO.getComment()));
//    }

    @Test
    void getComments() throws Exception {
        mockMvc.perform(get("/api/home/post/comments/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void putComment() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = commentService.getComment(1L);
        commentDTO.setComment("New Test Comment");

        mockMvc.perform(put("/api/home/post/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentDTO))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value(commentDTO.getComment()));

    }

    @Test
    void deleteComment() throws Exception {
        int sizeBefore = commentService.getComments().size();
        Long id = 1L;
        mockMvc.perform(delete("/api/home/post/{id}", id))
                .andExpect(status().isOk());
        int sizeAfter = commentService.getComments().size();

        assertThat(sizeBefore).isNotEqualTo(sizeAfter);
        assertThat(sizeBefore - 1).isEqualTo(sizeAfter);

    }
}