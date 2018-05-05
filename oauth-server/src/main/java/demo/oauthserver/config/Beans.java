package demo.oauthserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import demo.oauthserver.util.SHA512Encoder;

@Configuration
public class Beans {
	/** Security **/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SHA512Encoder();
	}
	
	@Bean
    public RestTemplate restTemplateWithoutBalanced() {
        return new RestTemplate();
    }
}
