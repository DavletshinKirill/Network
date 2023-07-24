package dev.vorstu.services;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.BaseRole;
import dev.vorstu.db.entities.Post;
import dev.vorstu.db.entities.RoleUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.dto.UserDTO;
import dev.vorstu.mappers.PostMapper;
import dev.vorstu.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private AuthUserRepo authUserRepo;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;
    @Value("${user.defaultPicture}")
    private String defaultPicture;


    public AuthUserEntity getLoggedUser() {
        return authUserRepo.findAll().stream().filter(el -> el.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())).findAny().get();
    }

    public UserDTO getUserById(Long id) {
        return UserMapper.INSTANCE.toDto(authUserRepo.findById(id).get());
    }

    public PostDTO addPost(PostDTO postDTO) {
        Post post = PostMapper.INSTANCE.toEntity(postDTO);
        AuthUserEntity user = this.getLoggedUser();
        user.addPost(post);
        authUserRepo.save(user);
        return postDTO;
    }

    public AuthUserEntity createUser(AuthUserEntity user) {
        AuthUserEntity user1 = new AuthUserEntity(true, user.getUsername(), user.getPassword(), Collections.singleton(new RoleUserEntity(user.getUsername(), BaseRole.STUDENT)));
        user1.setMainPhoto(defaultPicture);
        return authUserRepo.save(user1);
    }

    public Set<AuthUserEntity> getAllUsers() {
        return (Set<AuthUserEntity>) authUserRepo.findAll();
    }

    public Long deleteUser(Long id) {
        authUserRepo.deleteById(id);
        return id;
    }

    public AuthUserEntity updateUser(AuthUserEntity user) {
        AuthUserEntity user1 = authUserRepo.findById(user.getId()).get();

        user1.setMainPhoto(user.getMainPhoto());
        user1.setUsername(user.getUsername());

        return authUserRepo.save(user1);
    }

    public UserDTO updatePhoto(UserDTO userDTO) {
        AuthUserEntity user = authUserRepo.findById(userDTO.getId()).get();
        user.setMainPhoto(userDTO.getMainPhoto());
        return UserMapper.INSTANCE.toDto(authUserRepo.save(user));
    }

    public UserDTO updateUserName(UserDTO userDTO) {
        AuthUserEntity user = authUserRepo.findById(userDTO.getId()).get();
        user.setUsername(userDTO.getUsername());
        return UserMapper.INSTANCE.toDto(authUserRepo.save(user));
    }
}
