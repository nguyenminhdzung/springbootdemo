package demo.api.repository.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findFirstByName(String name);
}
