package demo.api.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionMapper.class);
			
	@Override
	public Response toResponse(Throwable exception) {
		
		logger.warn("Error occured when processing", exception);
		
		if (exception instanceof WebApplicationException) {
			WebApplicationException wae = (WebApplicationException) exception;
			if (!wae.getResponse().getStatusInfo().equals(Status.INTERNAL_SERVER_ERROR))
				return wae.getResponse();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                	   .entity("Error occured when processing in server.")
                	   .type(MediaType.TEXT_HTML).build();
	}
}
