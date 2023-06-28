package dev.vorstu.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private int id;
    private String title;
    private String photo;
    private int likes;

    public PostDTO(int id, String photo, String title, int likes) {
        this.id = id;
        this.photo = photo;
        this.likes = likes;
        this.title = title;
    }

    public PostDTO(String photo, String title, int likes) {
        this.photo = photo;
        this.likes = likes;
        this.title = title;
    }
}
