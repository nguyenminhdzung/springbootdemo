package demo.api.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.api.model.User;

public interface UserRepositoryCustom {
	public Page<User> findAll(Pageable pageable, UserSearchInfo searchInfo);
}
