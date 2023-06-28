package dev.vorstu.config;

import dev.vorstu.db.entities.AuthUserEntity;
import dev.vorstu.db.entities.BaseRole;
import dev.vorstu.db.entities.RoleUserEntity;
import dev.vorstu.db.repositories.AuthUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class Initializer {
	
	@Autowired
	private AuthUserRepo authUserRepo;

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
