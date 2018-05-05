package demo.oauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
@ComponentScan({"demo.oauthserver", 
				"demo.shared.service"})
@EntityScan(basePackages={"demo.shared.model", "demo.oauthserver.model"})
public class Application {
		
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
