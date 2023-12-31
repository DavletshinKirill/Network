package dev.vorstu.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class AuthUserEntity extends BaseEntity {
  static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  public AuthUserEntity(boolean enabled, String username, String password, Set roles) {
    this.password = passwordEncoder.encode(password);
    this.enabled = enabled;
    this.username = username;
    this.roles = roles;
  }
  
  
  public AuthUserEntity(boolean enabled, String username, String password, String mainPhoto, Set roles) {
	    this.password = passwordEncoder.encode(password);
	    this.enabled = enabled;
	    this.username = username;
	    this.roles = roles;
	    this.mainPhoto = mainPhoto;
	  }
  
  
  private String username;
  private boolean enabled;
  private String password;
  private String mainPhoto;
  
  public void addPost(Post post) {
	  this.posts.add(post);
  }
  
  public void addMainPhoto(String photo) {
	  this.mainPhoto = mainPhoto;
  }
  
  public void clearPosts() {
	  if (this.posts != null)
		  this.posts.clear();
	  
  }


  @OneToMany(cascade={CascadeType.ALL},
    orphanRemoval=true)
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinColumn(name="user_id", updatable=true)
  private Set<RoleUserEntity> roles;
  
  @OneToMany(cascade={CascadeType.ALL})
		  @LazyCollection(LazyCollectionOption.FALSE)
		  @JoinColumn(name="user_id", updatable=true)
		  private Set<Post> posts;
   
}


