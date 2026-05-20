package vn.devpro.javaweb35.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.devpro.javaweb35.dto.CategoryDto;
import vn.devpro.javaweb35.model.Category;
import vn.devpro.javaweb35.model.User;
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

	public List<Category> findAllAndSortByName() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(Sort.by("name").ascending());
	}

	public List<Category> findAllAndSort(String column, String direction) {
		// TODO Auto-generated method stub
		if (column == null || column.isBlank()) {
			return findAll();
		}
		Sort sort = ("desc".equalsIgnoreCase(direction)) ? Sort.by(column).descending() : Sort.by(column).ascending();
		return categoryRepository.findAll(sort);
	}

	public Page<Category> findAllSortAndPageable(PageRequest pageable) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(pageable);
	}

//	public Page<Category> findAllSortPageableAndSearch(String name, LocalDateTime fromInclusive,
//			LocalDateTime toInclusive, PageRequest pageable) {
//		// TODO Auto-generated method stub
//
//		Specification<Category> specification = Specification.where(null);
//		if (name != null && !name.isBlank()) {
//			specification = specification
//					.and((root, q, cb) -> cb.like(cb.lower(root.get("name")), 
//							"%" + name.toLowerCase() + "%"));
//		}
//		
//		if(fromDate != null) {
//			specification = specification.and((root, query, criteriaBuilder) -> 
//			criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"),fromDate));
//		}
//		
//		if(toDate!=null) {
//			specification = specification.and((root, query, criteriaBuilder) -> 
//			criteriaBuilder.lessThanOrEqualTo(root.get("createDate"),toDate));
//		}
//		return categoryRepository.findAll(specification,pageable);
//	}

	public Category insert(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = new Category();
		category.setId(null);

		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setStatus(categoryDto.getStatus() != null ? category.getStatus() : Boolean.TRUE);
		category.setCreateDate(categoryDto.getCreateDate());
		category.setUpdateDate(categoryDto.getUpdateDate());

		// Có chọn category cha
		if (categoryDto.getCategoryId() != null && categoryDto.getCategoryId() != 0) {
			category.setParentCategory(categoryRepository.getReferenceById(categoryDto.getCategoryId()));
		} else {
			category.setParentCategory(null);
		}

		if (categoryDto.getCreateById() != null && categoryDto.getCreateById() != 0) {
			User createBy = userRepository.getReferenceById(categoryDto.getCreateById());
			category.setCreateBy(createBy);
		} else {
			category.setCreateBy(null);
		}

		return categoryRepository.save(category);

	}

	public Category update(Integer id, CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = findById(id);

		category.setName(categoryDto.getName());
		category.setCreateDate(categoryDto.getCreateDate());
		category.setUpdateDate(categoryDto.getUpdateDate());
		category.setDescription(categoryDto.getDescription());
		category.setStatus(categoryDto.getStatus());

		if (categoryDto.getCategoryId() != null && categoryDto.getCategoryId() > 0) {
			category.setParentCategory(categoryRepository.getReferenceById(categoryDto.getCategoryId()));
		} else {
			category.setParentCategory(null);
		}

		// Co cho sua Create by, khong thi bo doan code nay
		if (categoryDto.getCreateById() != null && categoryDto.getCreateById() != 0) {
			User createBy = userRepository.getReferenceById(categoryDto.getCreateById());
			category.setCreateBy(createBy);
		} else {
			category.setCreateBy(null);
		}

		// Cap nhat Update by
		if (categoryDto.getUpdateById() != null && categoryDto.getUpdateById() > 0) {
			category.setUpdateBy(userRepository.getReferenceById(categoryDto.getUpdateById()));
		}
		return categoryRepository.save(category);
	}

	public void inactive(Integer id) {
		Category category = findById(id);

		if (category == null) {
			return;
		}

		category.setStatus(false);

		categoryRepository.save(category);
	}
}
