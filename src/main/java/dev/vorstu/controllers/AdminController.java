package dev.vorstu.controllers;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.services.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/admin")
@Slf4j
@Api("Контроллер для пользователей")
public class AdminController {


	@Autowired
	private UserService userService;
	
	@GetMapping("users")
	public Set<AuthUserEntity> getAllUsers() {
		return (Set<AuthUserEntity>) userService.getAllUsers();
	}
	
	@PostMapping(value="users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public AuthUserEntity createUser(@RequestBody AuthUserEntity user) {
		return userService.createUser(user);
	}
	
	@PutMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthUserEntity changeUser(@RequestBody AuthUserEntity user) {
		return userService.updateUser(user);
	}
	
	@DeleteMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long deleteUser(@PathVariable("id")Long id) {
		userService.deleteUser(id);
		return id;
	}

}

