package dev.vorstu.controllers;

import dev.vorstu.adapter.WebSocketService;
import dev.vorstu.dto.CommentDTO;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.services.CommentService;
import dev.vorstu.services.PostService;
import dev.vorstu.services.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/home/post")
@Slf4j
@Api("Контроллер для комментов")
public class PostController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;

	@GetMapping("{id}")
	public PostDTO GetPost(@PathVariable("id")int id)
	{
		return postService.getPostDTOById(id);
	}
	
/*	@MessageMapping("/comments")
	@SendTo("/topic/comments/{id}")
	public CommentDTO addComment(@RequestParam("id")Long id, @RequestBody CommentDTO comment)
	{
		 log.warn("Web-socket is working");
	     return commentService.addComment(id, comment);
	}*/
	
	@GetMapping("comments/{id}")
	public Page<CommentDTO> getComments(@PathVariable("id")Long id, @RequestParam(defaultValue = "0") int pageNo,
										@RequestParam(defaultValue = "5") int pageSize)
	{
		return commentService.getComments(id, pageNo, pageSize);
	}

	@PutMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CommentDTO putComment(@PathVariable("id")Long id, @RequestBody CommentDTO comment)
	{
		this.notifyFrontend();
		return commentService.putComment(comment);
	}

	@DeleteMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long deleteComment(@PathVariable("id")Long id)
	{
		this.notifyFrontend();
		commentService.deleteComment(id);
		return id;
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
