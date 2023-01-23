package dev.vorstu.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import dev.vorstu.adapter.WebSocketService;
import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Comments;
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
		newpost.setPhoto("https://material.angular.io/assets/img/examples/shiba2.jpg");
		Optional<AuthUserEntity> user = authUserRepo.findById((long)id);
		AuthUserEntity user1 = user.get();
		user1.addPost(newpost);		
		this.notifyFrontend();
		return authUserRepo.save(user1);
	}
	
	@PostMapping(value="post", produces = MediaType.APPLICATION_JSON_VALUE)
	public Posts audatePost(@RequestBody Posts newpost)
	{
		this.notifyFrontend();
		return postRepo.save(newpost);
	}
	
	private AuthUserEntity getUser() {
		Long id;
		AuthUserEntity currentUser = null;
        for (AuthUserEntity itVar : authUserRepo.findAll())
        {
            if (itVar.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            	currentUser = itVar;
            }
        }
		return currentUser;
	}

	
}
