package dev.vorstu.dto;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private int id;
    private String comment;
    private AuthUserEntity user;
    private Post post;

    public CommentDTO(int id, String comment, AuthUserEntity user, Post post) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.post = post;
    }
}
