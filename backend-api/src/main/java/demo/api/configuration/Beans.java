package demo.api.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.RestTemplate;

import demo.api.service.DemoDataCreator;
import demo.api.service.common.MessageSourceService;
import demo.api.util.SHA512Encoder;
import demo.api.util.UnauthorizedEntryPoint;

@Configuration
public class Beans {
	
	/** Data **/
	@Bean(initMethod="initData")
	public DemoDataCreator demoDataCreator() {
		return new DemoDataCreator();
	}
		
	/** Rest **/
	@LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	/** Security **/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SHA512Encoder();
	}
	
	@Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
		return new UnauthorizedEntryPoint();
	}
	
	/** Resource Bundle **/
	@Bean
	public MessageSource errorMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:locale/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
	@Bean
	public MessageSourceService messageSourceService() {
		return new MessageSourceService();
	}
}
