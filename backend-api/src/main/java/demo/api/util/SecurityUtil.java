package demo.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
			
	public static String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null) {
			logger.info("Authentication: null");
			return null;
		}
		
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && principal.equals("anonymousUser")) {
            return null;
        }
        
        if (principal instanceof UserDetails) {
        	UserDetails userDetails = (UserDetails) principal;
	
	        return userDetails.getUsername();
        }
        
        return authentication.getName();
	}
}
