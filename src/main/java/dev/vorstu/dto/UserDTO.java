package dev.vorstu.dto;


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
    private Set<PostDTO> posts;
    private int id;

    public UserDTO(int id, String username, String mainPhoto) {
        this.id = id;
        this.username = username;
        this.mainPhoto = mainPhoto;
        this.posts = posts;
    }



    public void addPost(PostDTO post) {
        this.posts.add(post);
    }
}
