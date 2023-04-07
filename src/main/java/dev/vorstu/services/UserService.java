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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private AuthUserRepo authUserRepo;
    @Value("${user.defaultPicture}")
    private String defaultPicture;


    public AuthUserEntity getUser() {
        return authUserRepo.findAll().stream().filter(el -> el.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())).findAny().get();
    }

    public AuthUserEntity getUserById(Long id) {
        return authUserRepo.findById(id).get();
    }

    public UserDTO addPost(Long id, PostDTO postDTO) {
        Post post = PostMapper.INSTANCE.toEntity(postDTO);
        post.setPhoto(defaultPicture);
        AuthUserEntity user = authUserRepo.findById(id).get();
        user.addPost(post);
        return UserMapper.INSTANCE.toDto(authUserRepo.save(user));
    }

    public AuthUserEntity createUser(AuthUserEntity user) {
        AuthUserEntity user1 = new AuthUserEntity(true, user.getUsername(), user.getPassword(), Collections.singleton(new RoleUserEntity(user.getUsername(), BaseRole.STUDENT)));
        user1.addMainPhoto(defaultPicture);
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new RoleUserEntity(user.getUsername(), BaseRole.STUDENT)));
        AuthUserEntity user1 = authUserRepo.findAll().stream().filter(el -> el.getId() == user.getId()).findAny().get();
        user.setEnabled(true);
        user.setPosts(user1.getPosts());
        user.setMainPhoto(user1.getMainPhoto());
        return authUserRepo.save(user);
    }
}
