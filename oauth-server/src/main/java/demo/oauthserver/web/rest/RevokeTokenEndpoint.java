package demo.oauthserver.web.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
public class RevokeTokenEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(RevokeTokenEndpoint.class);
			
    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
    	logger.info("Calling: /oauth/token/revoke");
        
    	String accessToken = request.getParameter("accessToken");
    	if (StringUtils.isNotBlank(accessToken)) {
            
            tokenServices.revokeToken(accessToken);
        }
    }

}