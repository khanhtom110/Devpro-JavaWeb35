package vn.devpro.javaweb35.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.devpro.javaweb35.model.Product;
import vn.devpro.javaweb35.repository.CategoryRepository;
import vn.devpro.javaweb35.repository.ProductRepository;
import vn.devpro.javaweb35.repository.UserRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;

	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
			UserRepository userRepository) {
		super();
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.userRepository = userRepository;
	}

	public Product findById(Integer id) {
		return productRepository.findById(id).orElse(null);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Boolean existsByName(String name) {
		return productRepository.existsByName(name);
	}
}
