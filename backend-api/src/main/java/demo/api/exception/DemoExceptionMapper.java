package demo.api.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import demo.api.service.common.MessageSourceService;
import demo.api.service.common.UtilService;
import demo.api.web.APIOutputInfo;

@Provider
public class DemoExceptionMapper implements ExceptionMapper<DemoException> {
	
	@Autowired
	private MessageSourceService messageSourceService;
	
	@Autowired
	private UtilService utilService;
	
	@Override
	public Response toResponse(DemoException exception) {
				
		String error = messageSourceService.getMessage(utilService.getCurrentUserLanguage(), exception.getErrorCode(), "Error");
		
        return Response.status(Response.Status.OK)
                       .entity(new APIOutputInfo(false, exception.getErrorCode(), error))
                       .type(MediaType.APPLICATION_JSON).build();
	}

}
