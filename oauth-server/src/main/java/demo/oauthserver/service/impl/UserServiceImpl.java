package demo.oauthserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.oauthserver.model.User;
import demo.oauthserver.repository.user.UserRepository;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	@Lazy
    private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding User by username: " + username);
		
		User user = userRepository.findFirstByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("The user with name " + username + " was not found");
        }
        logger.debug("User by username found: " + user);
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}
}
