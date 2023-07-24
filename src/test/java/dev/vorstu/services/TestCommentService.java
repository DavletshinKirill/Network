package dev.vorstu.services;

import dev.vorstu.dto.CommentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("/application.yaml")
@Sql( value = {"/scripts/DropTables.sql", "/scripts/CreateTables.sql",
        "/scripts/AddForeignKey.sql", "/scripts/insert/InsertUsers.sql",
        "/scripts/insert/InsertPosts.sql", "/scripts/insert/InsertComments.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@WithMockUser("admin")
public class TestCommentService {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentService commentService;

    @Test
    public void addComment(){
        CommentDTO commentDTO = new CommentDTO("ads");
        commentService.addComment((long)3, commentDTO);
        assertThat(commentService.getComment(4).getComment()).isEqualTo(commentDTO.getComment());
    }

    @Test
    public void putComment(){
        CommentDTO commentDTO = commentService.getComment(1);
        commentDTO.setComment("asd");
        commentService.putComment(commentDTO);
        assertThat(commentService.getComment(1).getComment()).isEqualTo(commentDTO.getComment());
    }

    @Test
    public void deleteComment() {
        CommentDTO commentDTO = commentService.getComment(1);
        int sizeBefore = commentService.getComments().size();
        commentService.deleteComment(1L);
        int sizeAfter = commentService.getComments().size();

        assertThat(sizeBefore).isNotEqualTo(sizeAfter);
        assertThat(sizeBefore - 1).isEqualTo(sizeAfter);
    }

    @Test
    public void deleteComments() {
        ArrayList<CommentDTO> commentsList = new ArrayList<CommentDTO>();
        commentsList.add(commentService.getComment(1));
        commentsList.add(commentService.getComment(2));
        int sizeBefore = commentService.getComments().size();
        commentService.deleteComments(commentsList);
        int sizeAfter = commentService.getComments().size();
        assertThat(sizeBefore).isNotEqualTo(sizeAfter);
        assertThat(sizeBefore - commentsList.size()).isEqualTo(sizeAfter);
    }

}
