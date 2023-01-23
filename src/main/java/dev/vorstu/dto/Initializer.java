package dev.vorstu.dto;

import java.util.ArrayList;
import java.util.Collections;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import dev.vorstu.controllers.AdminController;
import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.BaseEntity;
import dev.vorstu.db.entities.BaseRole;
import dev.vorstu.db.entities.Comments;
import dev.vorstu.db.entities.Posts;
import dev.vorstu.db.entities.RoleUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;
import dev.vorstu.db.repositories.CommentRepo;
import dev.vorstu.db.repositories.PostRepo;
import lombok.extern.slf4j.Slf4j;

@Component
public class Initializer {
	
	@Autowired
	private AuthUserRepo authUserRepo;

	@Autowired
	private CommentRepo comments;
	
	@Autowired
	private PostRepo posts;
	



	
	public void initial() {      
	    
	    AuthUserEntity user1 = new AuthUserEntity(true, "user1", "123456", "https://material.angular.io/assets/img/examples/shiba2.jpg",
	    		Collections.singleton(new RoleUserEntity("user1", BaseRole.STUDENT))
	    		);
	    
	    authUserRepo.save(user1);
	    

	    
	    AuthUserEntity user2 = new AuthUserEntity(true, "user2", "123456","https://material.angular.io/assets/img/examples/shiba2.jpg", 
	    		Collections.singleton(new RoleUserEntity("user", BaseRole.STUDENT)));
	    
	    authUserRepo.save(user2);

	    

	    AuthUserEntity admin = new AuthUserEntity(true, "admin", "1234", "https://material.angular.io/assets/img/examples/shiba2.jpg",
	    		Collections.singleton(new RoleUserEntity("admin", BaseRole.SUPER_USER)));
	    
	    authUserRepo.save(admin);
	    

	    AuthUserEntity admin1 = new AuthUserEntity(true, "admin1", "12345", "https://material.angular.io/assets/img/examples/shiba2.jpg",
	    		Collections.singleton(new RoleUserEntity("admin1", BaseRole.SUPER_USER)));
	    authUserRepo.save(admin1);
}
}
