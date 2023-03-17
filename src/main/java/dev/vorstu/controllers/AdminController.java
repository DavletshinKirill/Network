package dev.vorstu.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.BaseRole;
import dev.vorstu.db.entities.Posts;
import dev.vorstu.db.entities.RoleUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("api/admin")
@Slf4j
public class AdminController {

	@Value("${user.defaultPicture}")
	private String defaultPicture;

	@Autowired
	private AuthUserRepo authUserRepo;
	
	@GetMapping("users")
	public Set<AuthUserEntity> getAllUsers() {
		return (Set<AuthUserEntity>) authUserRepo.findAll();
	}
	
	@PostMapping(value="users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public AuthUserEntity createStudent(@RequestBody AuthUserEntity user) {
		AuthUserEntity user1 = new AuthUserEntity(true, user.getUsername(), user.getPassword(), Collections.singleton(new RoleUserEntity(user.getUsername(), BaseRole.STUDENT)));
		user1.addMainPhoto(defaultPicture);
		return authUserRepo.save(user1);
	}
	
	@PutMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthUserEntity changeStudent(@RequestBody AuthUserEntity student) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		student.setPassword(passwordEncoder.encode(student.getPassword()));
		student.setRoles(Collections.singleton(new RoleUserEntity(student.getUsername(), BaseRole.STUDENT)));
		AuthUserEntity user = getUser(student.getId());
		student.setEnabled(true);
		student.setPosts(user.getPosts());
		student.setMainPhoto(user.getMainPhoto());
		return authUserRepo.save(student);
	}
	
	@DeleteMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long deleteStudent(@PathVariable("id")Long id) {
		authUserRepo.deleteById(id);
		return id;
	}
	
	private AuthUserEntity getUser(Long id) {
		AuthUserEntity currentUser = null;
        for (AuthUserEntity itVar : authUserRepo.findAll())
        {
            if (itVar.getId() == id) {
            	currentUser = itVar;
            	break;
            }
        }
		return currentUser;
	}

}

