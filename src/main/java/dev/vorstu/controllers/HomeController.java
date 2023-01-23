package dev.vorstu.controllers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.BaseRole;
import dev.vorstu.db.entities.RoleUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;
import dev.vorstu.dto.ShortUser;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;
@RestController
@RequestMapping("api/home")
public class HomeController {

	@Autowired
	private AuthUserRepo authUserRepo;
	
	
	
	@GetMapping("users")
	public Set<AuthUserEntity> getAllUsers() {
		return (Set<AuthUserEntity>) authUserRepo.findAll();
	}
	
	@PostMapping(value="users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public AuthUserEntity createStudent(@RequestBody AuthUserEntity user) {
		 AuthUserEntity user1 = new AuthUserEntity(true, user.getUsername(), user.getPassword(), Collections.singleton(new RoleUserEntity("user", BaseRole.STUDENT)));
		return authUserRepo.save(user1);
	}
//	@PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
//	public AuthUserEntity changeStudent(@RequestBody AuthUserEntity student)
//	{
//		AuthUserEntity changingStudent = new AuthUserEntity(true, student.getName(), student.getMainPhoto(), student.getPassword());		
//		return authUserRepo.save(changingStudent);
//	}
	
	
	@DeleteMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long deleteStudent(@PathVariable("id")Long id)
	{
		authUserRepo.deleteById(id);
		return id;
	}

	
	
}

