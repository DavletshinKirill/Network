package dev.vorstu.db.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Posts extends BaseEntity {
	
	
	public Posts(String photo, String title, int likes, Set<Comments> comments) {
		this.photo = photo;
		this.likes = likes;
		this.title = title;
	}
	
	
	public Posts(String photo, String title, int likes) {
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
