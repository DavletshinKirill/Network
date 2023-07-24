package dev.vorstu.controllers;


import dev.vorstu.adapter.WebSocketService;
import dev.vorstu.dto.PostDTO;
import dev.vorstu.dto.UserDTO;
import dev.vorstu.services.PostService;
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
	private PostService postService;


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
	public UserDTO GetPost(@PathVariable("id")Long id)
	{
		return userService.getUserById(id);
	}

	@PostMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PostDTO addPost(@RequestBody PostDTO postDTO)
	{
		this.notifyFrontend();
		return userService.addPost(postDTO);
	}

	@PutMapping(value="update", produces = MediaType.APPLICATION_JSON_VALUE)
	public PostDTO updatePost(@RequestBody PostDTO postDTO)
	{
		this.notifyFrontend();
		return postService.updatePost(postDTO);
	}

	@PutMapping(value="updatePhoto", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO updateUserPhoto(@RequestBody UserDTO userDTO)
	{
		this.notifyFrontend();
		return userService.updatePhoto(userDTO);
	}

	@PutMapping(value="updateUsername", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO updateUsername(@RequestBody UserDTO userDTO)
	{
		this.notifyFrontend();
		return userService.updateUserName(userDTO);
	}

	@DeleteMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Long deletePost(@PathVariable("id")Long id)
	{
		log.warn("delete post");
		this.notifyFrontend();
		postService.deletePost(id);
		return id;
	}



}
