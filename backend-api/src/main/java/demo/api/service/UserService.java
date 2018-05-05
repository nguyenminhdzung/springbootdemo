package demo.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.api.exception.DemoException;
import demo.api.model.Role;
import demo.api.model.User;
import demo.api.repository.user.UserRepository;
import demo.api.repository.user.UserSearchInfo;
import demo.api.service.common.BaseService;
import demo.api.util.Constants;
import demo.api.web.dto.user.UserInputInfo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService extends BaseService<User> {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
			
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleService roleService;
	
	@Override
	public String defaultSortField() {
		return "username";
	}
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ProfileService profileService;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByID(String userID) {
        return userRepository.findOne(userID);
    }

    public User getByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }
    
    public Page<User> getAll(int pageIndex, int pageSize, String sortField, String sortDirection, String keyword) {
    	UserSearchInfo searchInfo = new UserSearchInfo();
    	searchInfo.setKeyword(keyword);
    	
        return userRepository.findAll(constructPageSpecification(pageIndex, pageSize, sortField, sortDirection), searchInfo);
    }

	public User addNewUser(UserInputInfo inputInfo) {
		if (inputInfo.getRoles() == null || inputInfo.getRoles().size() <= 0)
            throw new DemoException(Constants.ErrorCode.USER_ROLE_NOT_SET);
		
        User found = userRepository.findFirstByUsername(inputInfo.getUsername());

        if (found != null)
            throw new DemoException(Constants.ErrorCode.USERNAME_DUPLICATED);
        
        User user = new User();
        bindUserInfo(inputInfo, user);
        String password = inputInfo.getPassword();        
        password = passwordEncoder.encode(password);
        
        user.setPassword(password);
        
        return userRepository.save(user);
	}

	private void bindUserInfo(UserInputInfo userInfo, User user) {
		
		user.setUsername(userInfo.getUsername());
        user.setFirstname(userInfo.getFirstname());
        user.setLastname(userInfo.getLastname());
        user.setEmail(userInfo.getEmail());
        user.setAddress1(userInfo.getAddress1());
        user.setAddress2(userInfo.getAddress2());
        user.setAddress3(userInfo.getAddress3());
        
        Set<Role> roles = parseRoles(userInfo);
        
        if (roles.size() <= 0)
        	throw new DemoException(Constants.ErrorCode.USER_ROLE_NOT_SET);
        
        user.setRoles(roles);
	}
	
	private Set<Role> parseRoles(UserInputInfo userInfo) {
        if (userInfo.getRoles() != null) {
        	Set<Role> roles = new HashSet<>();

            for (String roleName : userInfo.getRoles()) {
                Role role = roleService.getByName(roleName);

                if (role != null) {
                    roles.add(role);
                }
            }

            return roles;
        }

        return null;
    }

	public void delete(String userID) {
		User user = getByID(userID);
		
		User current = profileService.getProfile();
		
		if (current.getUsername().equals(user.getUsername()))
			throw new DemoException(Constants.ErrorCode.CANNOT_DELETE_CURRENT_PROFILE);
		
        userRepository.delete(userID);
    }

	public User save(UserInputInfo inputInfo) {
        if (inputInfo.getRoles() == null || inputInfo.getRoles().size() <= 0)
            throw new DemoException(Constants.ErrorCode.USER_ROLE_NOT_SET);
		
		User found = userRepository.findOne(inputInfo.getId());

        if (found == null || !found.getUsername().equals(inputInfo.getUsername()))
            throw new DemoException(Constants.ErrorCode.USERNAME_NOT_EXIST);

        bindUserInfo(inputInfo, found);
        
        return userRepository.save(found);
    }
}
