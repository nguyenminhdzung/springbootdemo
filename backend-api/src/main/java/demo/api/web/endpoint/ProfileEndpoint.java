package demo.api.web.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import demo.api.model.User;
import demo.api.service.ProfileService;
import demo.api.service.UserService;
import demo.api.service.common.UtilService;
import demo.api.web.APIOutputInfo;
import demo.api.web.dto.profile.ChangePasswordInput;
import demo.api.web.dto.profile.ProfileChangeInfo;
import demo.api.web.dto.user.UserViewInfo;

@Component
@Path("/profile")
public class ProfileEndpoint {
	
	@Autowired
    private ProfileService profileService;
	    
    @Autowired
    private TokenStore tokenStore;
	
    @Autowired
	UtilService utilService;

	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public APIOutputInfo<UserViewInfo> updateProfile(@Context HttpServletRequest request, ProfileChangeInfo userInfo) {
        
        User updated = profileService.updateProfile(userInfo);
        if (updated != null) {
        	return utilService.successResult(new UserViewInfo(updated));
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public APIOutputInfo<UserViewInfo> getProfile() {
        User user = profileService.getProfile();

        if (user == null) {
            throw new WebApplicationException(401);
        }
        
        UserViewInfo userInfo = new UserViewInfo(user);
        
        return utilService.successResult(userInfo);
    }

    @Path("changePassword")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public APIOutputInfo<UserViewInfo> changePassword(@Context HttpServletRequest request, ChangePasswordInput passwordInput) {
    	profileService.changePassword(passwordInput.getPassword(), passwordInput.getNewPassword());
        invalidateAccessToken(request);

        return getProfile();
    }

	private void invalidateAccessToken(HttpServletRequest request) {
		try {
			String authHeader = request.getHeader("Authorization");
	        if (authHeader != null) {
	            String tokenValue = authHeader.replace("Bearer", "").trim();
	            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
	    		if (accessToken == null) {
	    			return;
	    		}
	    		if (accessToken.getRefreshToken() != null) {
	    			tokenStore.removeRefreshToken(accessToken.getRefreshToken());
	    		}
	    		tokenStore.removeAccessToken(accessToken);
	        }
		} catch (Exception e) { }
	}
}
