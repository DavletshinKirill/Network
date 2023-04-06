package dev.vorstu.controllers;

import dev.vorstu.adapter.WebSocketService;
import dev.vorstu.db.entities.Comment;
import dev.vorstu.db.entities.Post;
import dev.vorstu.services.CommentService;
import dev.vorstu.services.PostService;
import dev.vorstu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/home/post")
@Slf4j
public class PostController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;

	@GetMapping("{id}")
	public Optional<Post> GetPost(@PathVariable("id")int id)
	{
		return postService.GetPostById(id);
	}
	
	@PostMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Comment addComment(@PathVariable("id")Long id, @RequestBody Comment comment)
	{
		 this.notifyFrontend();
	     return commentService.AddComment(id, comment);
	}
	
	@GetMapping("comments/{id}")
	public ArrayList<Comment> getComments(@PathVariable("id")int id)
	{
		return commentService.getComments(id);
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
