package demo.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.api.model.Role;
import demo.api.repository.role.RoleRepository;
import demo.api.service.common.BaseService;

import java.util.List;

@Service
@Transactional
public class RoleService extends BaseService<Role> {

    @Autowired
    RoleRepository roleRepository;
    
    @Override
    public String defaultSortField() {
    	return "name";
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getByID(String roleID) {
        return roleRepository.findOne(roleID);
    }
    
    public Role getByName(String name) {
        return roleRepository.findFirstByName(name);
    }
}
