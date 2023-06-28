package dev.vorstu.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.BaseRole;
import dev.vorstu.db.entities.RoleUserEntity;
import dev.vorstu.dto.UserDTO;
import dev.vorstu.mappers.UserMapper;
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

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application.yaml")
@Sql( value = {"/scripts/DropTables.sql", "/scripts/CreateTables.sql",
        "/scripts/AddForeignKey.sql", "/scripts/insert/InsertUsers.sql",
        "/scripts/insert/InsertPosts.sql", "/scripts/insert/InsertComments.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@WithMockUser("admin")
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void createUser() throws Exception {
        AuthUserEntity user2 = new AuthUserEntity(true, "user2", "123456","https://material.angular.io/assets/img/examples/shiba2.jpg",
                Collections.singleton(new RoleUserEntity("user", BaseRole.STUDENT)));

        mockMvc.perform(post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user2)))
                        .andExpect(status().isOk());
    }


    @Test
    void changeUser() throws Exception {
        UserDTO user2 = userService.getUserById(1L);
        user2.setUsername("change name");
        mockMvc.perform(put("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(UserMapper.INSTANCE.toEntity(user2))))
                        .andExpect(status().isOk());
    }

    @Test
    void deleteUser() {
    }
}