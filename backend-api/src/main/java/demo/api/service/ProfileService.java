package demo.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.api.exception.DemoException;
import demo.api.model.User;
import demo.api.repository.user.UserRepository;
import demo.api.service.common.BaseService;
import demo.api.util.Constants;
import demo.api.util.SecurityUtil;
import demo.api.web.dto.profile.ProfileChangeInfo;

@Service
@Transactional
public class ProfileService extends BaseService<User> {

	private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);
			
	@Autowired
    UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getProfile() {
        String username = SecurityUtil.getCurrentUsername();
        
        if (username == null)
        	return null;
        
        return userRepository.findFirstByUsername(username);
    }
    
    public User updateProfile(ProfileChangeInfo userInfo) {

        User found = getProfile();

        if (found == null)
            throw new DemoException(Constants.ErrorCode.USERNAME_NOT_EXIST);
        
        found.setFirstname(userInfo.getFirstname());
        found.setLastname(userInfo.getLastname());
        found.setEmail(userInfo.getEmail());
        found.setAddress1(userInfo.getAddress1());
        found.setAddress2(userInfo.getAddress2());
        found.setAddress3(userInfo.getAddress3());
        
        return userRepository.save(found);

    }

    public void changePassword(String oldPassword, String newPassword) {
        User found = getProfile();

        if (found == null)
            throw new DemoException(Constants.ErrorCode.USERNAME_NOT_EXIST);

        String cryptedOldPassword = passwordEncoder.encode(oldPassword);
        if (!found.getPassword().equals(cryptedOldPassword))
            throw new DemoException(Constants.ErrorCode.PASSWORD_INVALID);

        String cryptedNewPassword = passwordEncoder.encode(newPassword);

        found.setPassword(cryptedNewPassword);
        userRepository.save(found);
    }
}
