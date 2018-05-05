package demo.api.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.api.model.User;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
    User findFirstByUsername(String username);

}
