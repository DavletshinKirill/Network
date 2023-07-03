package dev.vorstu.services;


import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.BaseRole;
import dev.vorstu.db.entities.RoleUserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("/test-application.yaml")
@Sql( value = {"/scripts/DropTables.sql", "/scripts/CreateTables.sql",
        "/scripts/AddForeignKey.sql", "/scripts/insert/InsertUsers.sql",
        "/scripts/insert/InsertPosts.sql", "/scripts/insert/InsertComments.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@WithMockUser("admin")
public class TestUserService {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;


    @Test
    public void addUser(){
        AuthUserEntity user2 = new AuthUserEntity(true, "user2", "123456","https://material.angular.io/assets/img/examples/shiba2.jpg",
                Collections.singleton(new RoleUserEntity("user", BaseRole.STUDENT)));
        userService.createUser(user2);

        assertThat(userService.getAllUsers()).isNotNull();
    }

/*    @Test
    public void deleteUser(){
        int sizeBefore = userService.getAllUsers().size();
        userService.deleteUser((long)1);
        int sizeAfter = userService.getAllUsers().size();
        assertThat(sizeBefore).isNotEqualTo(sizeAfter);
        assertThat(sizeBefore - 1).isEqualTo(sizeAfter);
    }*/

    @Test
    public void updateUser(){
        AuthUserEntity user = userService.getLoggedUser();
        Long id = user.getId();
        user.setUsername("test admin name");
        userService.updateUser(user);
        assertThat(userService.getUserById(id).getUsername()).isEqualTo(user.getUsername());
    }

}
