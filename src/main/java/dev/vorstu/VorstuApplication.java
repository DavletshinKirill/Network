package dev.vorstu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.vorstu.config.Initializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VorstuApplication {

	
	private static Initializer initiator;

	@Autowired
	public void setInitialLoader(Initializer initiator) {
		VorstuApplication.initiator = initiator;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(VorstuApplication.class, args);
		initiator.initial();
	}
}

