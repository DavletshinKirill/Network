package dev.vorstu.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

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

	
	  @ManyToOne()
			  @LazyCollection(LazyCollectionOption.FALSE)
			  @JoinColumn(name="user_id", updatable=true)
			  private AuthUserEntity user;
	  
	  @ManyToOne(cascade = CascadeType.ALL)
			  @LazyCollection(LazyCollectionOption.FALSE)
			  @JoinColumn(name="post_id", updatable=true)
			  private Post post;
	
	
}
