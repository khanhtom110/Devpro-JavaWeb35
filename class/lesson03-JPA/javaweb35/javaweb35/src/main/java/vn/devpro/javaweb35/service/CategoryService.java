package vn.devpro.javaweb35.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.devpro.javaweb35.model.Category;
import vn.devpro.javaweb35.repository.CategoryRepository;
import vn.devpro.javaweb35.repository.UserRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;

	public CategoryService(CategoryRepository categoryRepository, UserRepository repository) {
		super();
		this.categoryRepository = categoryRepository;
		this.userRepository = repository;
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public List<Category> findAllActive() {
		return categoryRepository.findByStatusTrue();
	}

	public Category findById(Integer id) {
		return categoryRepository.findById(id).orElse(new Category());
	}

	public Boolean existsByName(String name) {
		return categoryRepository.existsByName(name);
	}
}
