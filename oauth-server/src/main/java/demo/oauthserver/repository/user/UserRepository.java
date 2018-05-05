package demo.oauthserver.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.oauthserver.model.User;

public interface UserRepository extends JpaRepository<User, String>
{
	User findFirstByUsername(String username);
}