package vn.devpro.javaweb35.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.devpro.javaweb35.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findById(Integer id);

	public User findByUsername(String username);

	Optional<User> findByUsernameAndStatus(String username, Boolean status);
}
