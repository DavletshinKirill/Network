package dev.vorstu.controllers;


import dev.vorstu.adapter.WebSocketService;
import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.repositories.PostRepo;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.dto.UserDTO;
import dev.vorstu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/home/user/")
@Slf4j
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostRepo postRepo;

	
	@Autowired
	private WebSocketService webSocketService;


	protected String getEntityTopic() {
		return "vehicle";
	}

	private void notifyFrontend() {
		log.warn("notifyFrontend");
		final String entityTopic = this.getEntityTopic();
		if (entityTopic == null) {
			log.warn("Failed to get entity topic");
			return;
		}
		webSocketService.sendMessage(entityTopic);
	}

	@GetMapping("{id}")
	public AuthUserEntity GetPost(@PathVariable("id")Long id)
	{		
		return userService.getUserById(id);
	}
	
	@PostMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO addPost(@PathVariable("id")Long id, @RequestBody PostDTO postDTO)
	{
		this.notifyFrontend();
		return userService.addPost(id, postDTO);
	}

// Разберись на фронте с лайками
//	@PostMapping(value="post", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Post updatePost(@RequestBody Post postDTO)
//	{
//		this.notifyFrontend();
//		return postRepo.save(postDTO);
//	}

	
}
