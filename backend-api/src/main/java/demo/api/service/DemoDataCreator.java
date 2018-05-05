package demo.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import demo.api.model.Role;
import demo.api.model.User;
import demo.api.repository.role.RoleRepository;
import demo.api.repository.user.UserRepository;

@Transactional
public class DemoDataCreator {

	private static final Logger logger = LoggerFactory.getLogger(DemoDataCreator.class);
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void initData() {
    	logger.info("Creating demo data.");
    	
    	try {
    		String [] roles = new String [] { Role.ROLE_ADMIN, Role.ROLE_USER };
            for (int i = 0; i < roles.length; i++) {
                Role role = this.roleRepository.findFirstByName(roles[i]);
                if (role==null){
                    role = new Role();
                    role.setName(roles[i]);
                    this.roleRepository.save(role);
                }
            }
            
	        User defaultUser = this.userRepository.findFirstByUsername("admin");
	        if (defaultUser == null) {
	        	defaultUser = new User();
	            defaultUser.setUsername("admin");
	            String password = passwordEncoder.encode("admin");
	            defaultUser.setPassword(password);
	            
	            defaultUser.setFirstname("Admin");
	            defaultUser.setLastname("Admin");
	            defaultUser.setEmail("admin@demo.com");            
	            
	            defaultUser.setRoles(Sets.newHashSet(roleRepository.findFirstByName(Role.ROLE_ADMIN)));
	            
	            this.userRepository.save(defaultUser);
	        }
    	} catch (Exception e) {
    		logger.error("Error when Creating demo data.", e);
    	} finally {
    		logger.info("Finished Creating demo data.");
    	}
    }

}
