package dev.vorstu.controllers;

import dev.vorstu.dto.CommentDTO;
import dev.vorstu.services.CommentService;
import dev.vorstu.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @MessageMapping("/comments/{userId}/{postId}")
    @SendTo("/topic/comments")
    public CommentDTO addComment(@DestinationVariable("userId") String userId, @DestinationVariable("postId") String postId, CommentDTO commentDTO)
    {
        return commentService.addComment(commentDTO, Long.parseLong(userId), Long.parseLong(postId));
    }

    @MessageMapping("/comments/update")
    @SendTo("/topic/comments")
    public CommentDTO updateComment(CommentDTO comment)
    {
        return commentService.putComment(comment);
    }

    @MessageMapping("/comments/delete/{commentId}")
    @SendTo("/topic/comments/delete")
    public Long deleteComment(@DestinationVariable("commentId")Long commentId)
    {
        commentService.deleteComment(commentId);
        return commentId;
    }


}
