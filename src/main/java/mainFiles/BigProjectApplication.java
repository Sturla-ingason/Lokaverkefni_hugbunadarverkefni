package mainFiles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mainFiles.controllers.userController;

// Ólafur Kristófer okh4@hi.is

@SpringBootApplication
public class BigProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigProjectApplication.class, args);
		
		userController controller = new userController();
		controller.teset();
	}

}
