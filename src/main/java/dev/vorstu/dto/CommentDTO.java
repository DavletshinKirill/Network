package dev.vorstu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private long id;
    private String comment;
    private UserDTO user;
    private PostDTO post;

    public CommentDTO(int id, String comment, UserDTO user, PostDTO post) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.post = post;
    }

    public CommentDTO(String comment) {
        this.comment = comment;
    }
}
