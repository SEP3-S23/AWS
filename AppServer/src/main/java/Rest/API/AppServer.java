package Rest.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Specifying a bunch of shiet because apparently SpringBoot is dumb enough that you have to tell him everything twice. D:<
@SpringBootApplication
@EnableJpaRepositories(basePackages = "Rest.API.Repository")
@ComponentScan("Rest.API.Controllers")
@ComponentScan(basePackages = {"Rest.API.Service"})
@EntityScan(basePackages = "Rest.API.Model")
public class AppServer {

	public static void main(String[] args) {
		SpringApplication.run(AppServer.class, args);
	}

}
