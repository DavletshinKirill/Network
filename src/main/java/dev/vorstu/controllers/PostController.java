package dev.vorstu.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.vorstu.adapter.WebSocketService;
import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Comment;
import dev.vorstu.db.entities.Post;
import dev.vorstu.db.repositories.AuthUserRepo;
import dev.vorstu.db.repositories.CommentRepo;
import dev.vorstu.db.repositories.PostRepo;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/home/post")
@Slf4j
public class PostController {
	
	@Autowired
	private AuthUserRepo authUserRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	

	@GetMapping("{id}")
	public Optional<Post> GetPost(@PathVariable("id")int id)
	{
		return postRepo.findById((long)id);
	}
	
	@PostMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Comment addComment(@PathVariable("id")Long id, @RequestBody Comment comment)
	{

		 Optional<Post> postOptional = postRepo.findById(id);
		 Post newPost = postOptional.get();
		 
		 AuthUserEntity user = getUser();
		 
		 comment.setPost(newPost);
		 comment.setUser(user);
		 this.notifyFrontend();
	     return commentRepo.save(comment);
	}
	
	@GetMapping("comments/{id}")
	public ArrayList<Comment> getComments(@PathVariable("id")int id)
	{
		ArrayList<Comment> comments = new ArrayList<Comment>();
        for (Comment itVar : commentRepo.findAll())
        {
        	if(itVar.getPost().getId() == id)
        		comments.add(itVar);
        }

		return comments;
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


}
