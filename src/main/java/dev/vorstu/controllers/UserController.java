package dev.vorstu.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.vorstu.adapter.WebSocketService;
import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Posts;
import dev.vorstu.db.repositories.AuthUserRepo;
import dev.vorstu.db.repositories.PostRepo;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/home/user/")
@Slf4j
public class UserController {
	
	
	@Autowired
	private AuthUserRepo authUserRepo;
	
	@Autowired
	private PostRepo postRepo;

	@Value("${user.defaultPicture}")
	private String defaultPicture;
	
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
	public Optional<AuthUserEntity> GetPost(@PathVariable("id")Long id)
	{		
		return authUserRepo.findById(id);
	}
	
	@PostMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthUserEntity addPost(@PathVariable("id")int id, @RequestBody Posts newpost)
	{
		newpost.setPhoto(defaultPicture);
		Optional<AuthUserEntity> user = authUserRepo.findById((long)id);
		AuthUserEntity user1 = user.get();
		user1.addPost(newpost);		
		this.notifyFrontend();
		return authUserRepo.save(user1);
	}
	
	@PostMapping(value="post", produces = MediaType.APPLICATION_JSON_VALUE)
	public Posts updatePost(@RequestBody Posts newpost)
	{
		this.notifyFrontend();
		return postRepo.save(newpost);
	}

	
}
