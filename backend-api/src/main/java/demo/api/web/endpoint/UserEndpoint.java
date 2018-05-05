package demo.api.web.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import demo.api.model.User;
import demo.api.service.UserService;
import demo.api.service.common.UtilService;
import demo.api.util.PageInfo;
import demo.api.web.APIOutputInfo;
import demo.api.web.dto.DTOHelper;
import demo.api.web.dto.user.UserInputInfo;
import demo.api.web.dto.user.UserViewInfo;

@Component
@Path("/user")
public class UserEndpoint {
	
	private static final Logger logger = LoggerFactory.getLogger(UserEndpoint.class);
	
	@Autowired
	private UserService userService;
	
    @Autowired
	UtilService utilService;
	    
	@GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public APIOutputInfo<PageInfo> listUsers(@DefaultValue("0") @QueryParam("page") int pageIndex, @DefaultValue("10") @QueryParam("size") int pageSize,
            			 @QueryParam("field") String field, @QueryParam("direction") String direction, 
            			 @QueryParam("keyword") String keyword) throws JsonGenerationException, JsonMappingException {

		Page p = userService.getAll(pageIndex, pageSize, field, direction, keyword);
        return utilService.successResult(DTOHelper.convertToUserInfoPage(p));
    }
	
	@DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public APIOutputInfo<Void> delete(@Context HttpServletRequest request, @PathParam("id") String id) {
		userService.delete(id);
		return utilService.successResult(null);
    }
	
	@POST
	@Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public APIOutputInfo<UserViewInfo> create(@Context HttpServletRequest request, UserInputInfo userInfo) {
        User added = userService.addNewUser(userInfo);
        if (added != null) {
        	return utilService.successResult(new UserViewInfo(added));
        }
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("update")
    public APIOutputInfo<UserViewInfo> update(@Context HttpServletRequest request, UserInputInfo userInfo) {
        User updated = userService.save(userInfo);
        if (updated != null) {
        	return utilService.successResult(new UserViewInfo(updated));
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{id}")
    public APIOutputInfo<UserViewInfo> read(@PathParam("id") String id) {
        User user = userService.getByID(id);

        if (user == null) {
            throw new WebApplicationException(404);
        }

        return utilService.successResult(new UserViewInfo(user));
    }
	
}
