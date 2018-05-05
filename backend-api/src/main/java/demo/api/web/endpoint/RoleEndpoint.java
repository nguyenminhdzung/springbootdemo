package demo.api.web.endpoint;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import demo.api.model.Role;
import demo.api.service.RoleService;
import demo.api.service.common.UtilService;
import demo.api.web.APIOutputInfo;
import demo.api.web.dto.role.RoleInfo;

@Component
@Path("/role")
public class RoleEndpoint {
	
	@Autowired
    private RoleService roleService;
	
    @Autowired
	UtilService utilService;	
        
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public APIOutputInfo<List<RoleInfo>> listAll() throws JsonGenerationException, JsonMappingException {
    	List<RoleInfo> roles = Lists.newArrayList(Iterables.transform(roleService.getAll(), new Function<Role, RoleInfo>() {
			@Override
			public RoleInfo apply(Role role) {
				return new RoleInfo(role);
			}        	
        }));
    	
    	return utilService.successResult(roles);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{id}")
    public APIOutputInfo<RoleInfo> read(@PathParam("id") String id) {
        Role role = roleService.getByID(id);

        if (role == null) {
            throw new WebApplicationException(404);
        }

        return utilService.successResult(new RoleInfo(role));
    }
}