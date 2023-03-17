package dev.vorstu.db.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseEntity {
	
	
	public Post(String photo, String title, int likes, Set<Comment> comments) {
		this.photo = photo;
		this.likes = likes;
		this.title = title;
	}
	
	
	public Post(String photo, String title, int likes) {
		this.photo = photo;
		this.likes = likes;
		this.title = title;
	}
	
	@Column
	(
	name = "title",
	nullable = false,
	columnDefinition = "TEXT"
	)
	private String title;
	
	@Column
	(
	name = "photo",
	nullable = false,
	columnDefinition = "TEXT"
	)
	private String photo;
	
	@Column
	(
	name = "likes",
	nullable = false,
	columnDefinition = "bigint"
	)
	private int likes;
}
