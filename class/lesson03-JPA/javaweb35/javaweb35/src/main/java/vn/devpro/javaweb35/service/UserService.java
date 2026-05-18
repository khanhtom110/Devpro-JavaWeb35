package vn.devpro.javaweb35.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.devpro.javaweb35.model.User;
import vn.devpro.javaweb35.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
