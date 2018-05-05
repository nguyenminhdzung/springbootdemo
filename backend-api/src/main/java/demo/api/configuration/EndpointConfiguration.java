package demo.api.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import demo.api.exception.CommonExceptionMapper;
import demo.api.exception.DemoExceptionMapper;
import demo.api.web.endpoint.*;

@Component
@ApplicationPath("/v0")
public class EndpointConfiguration extends ResourceConfig {
	public EndpointConfiguration() {
		
		register(CommonExceptionMapper.class);		
		register(DemoExceptionMapper.class);

		register(VersionEndpoint.class);	
		register(ProfileEndpoint.class);
		register(UserEndpoint.class);
		register(RoleEndpoint.class);
	}
}
