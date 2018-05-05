package demo.api.web.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

@Component
@Path("/version")
public class VersionEndpoint {
		
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String version() {
		return "demo version";
	}
}
