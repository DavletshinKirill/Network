package dev.vorstu.db.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment extends BaseEntity {
	@Column
	(
	name = "comment",
	nullable = false,
	columnDefinition = "TEXT"
	)
	private String comment;
	public Comment(String comment) {
		this.comment = comment;
	}
	
	
	public void addUser(AuthUserEntity user) {
		this.user = user;
	}

	public void addPost(Post post) {
		this.post = post;
	}
	
	  @ManyToOne(cascade = CascadeType.ALL)
			  @LazyCollection(LazyCollectionOption.FALSE)
			  @JoinColumn(name="user_id", updatable=true)
			  private AuthUserEntity user;
	  
	  @ManyToOne(cascade = CascadeType.ALL)
			  @LazyCollection(LazyCollectionOption.FALSE)
			  @JoinColumn(name="post_id", updatable=true)
			  private Post post;
	
	
}
