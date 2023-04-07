package dev.vorstu.dto;


import dev.vorstu.db.entities.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String mainPhoto;
    private Set<Post> posts;
    private int id;

    public UserDTO(int id, String username, String mainPhoto, Set<Post> posts) {
        this.id = id;
        this.username = username;
        this.mainPhoto = mainPhoto;
        this.posts = posts;
    }
}
