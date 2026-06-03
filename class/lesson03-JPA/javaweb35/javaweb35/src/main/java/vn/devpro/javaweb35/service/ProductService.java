package vn.devpro.javaweb35.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import vn.devpro.javaweb35.dto.J35Constant;
import vn.devpro.javaweb35.dto.ProductDto;
import vn.devpro.javaweb35.model.Category;
import vn.devpro.javaweb35.model.Product;
import vn.devpro.javaweb35.model.ProductImage;
import vn.devpro.javaweb35.model.User;
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

	public Page<Product> search(String keyword, Integer categoryId, Boolean status, BigDecimal fromPrice,
			BigDecimal toPrice, LocalDateTime fromDate, LocalDateTime toDate, PageRequest pageable) {
		// TODO Auto-generated method stub
		Specification<Product> spec = Specification.where(null);

		if (keyword != null && !keyword.trim().isEmpty()) {
			String kw = "%" + keyword.trim().toLowerCase() + "%";
			spec = spec.and((root, q, cb) -> {
				q.distinct(true);
				List<Predicate> ors = new ArrayList<Predicate>();
				ors.add(cb.like(cb.lower(root.get("name")), kw));
				ors.add(cb.like(cb.lower(root.get("shortDescription")), kw));
				ors.add(cb.like(cb.lower(root.get("detailDescription")), kw));
				ors.add(cb.like(cb.lower(root.get("seo")), kw));

				Join<Product, Category> cat = root.join("category", JoinType.LEFT);
				ors.add(cb.like(cb.lower(root.get("name")), kw));
				return cb.or(ors.toArray(new Predicate[0]));
			});

			// category
			if (categoryId != null && categoryId != 0) {
				spec = spec.and((root, q, cb) -> {
					return cb.equal(root.get("category").get("id"), categoryId);
				});
			}

			// status
			if (status != null) {
				spec = spec.and((root, q, cb) -> cb.equal(root.get("status"), status));
			}

			// Price range
			if (fromPrice != null) {
				spec = spec.and((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("price"), fromPrice));
			}

			if (toPrice != null) {
				spec = spec.and((root, q, cb) -> cb.lessThanOrEqualTo(root.get("price"), toPrice));
			}

			if (fromDate != null) {
				// Lọc từ date
				// Nếu có giờ thì dùng LocalDate và LocalDateTime
				spec = spec.and((root, q, cb) -> cb.greaterThanOrEqualTo(root.get("createDate"), fromDate));
			}

			if (toDate != null) {
				spec = spec.and((root, q, cb) -> cb.lessThanOrEqualTo(root.get("createDate"), toDate));
			}
		}
		return productRepository.findAll(spec, pageable);
	}

	public Page<Product> search(String keyword, Pageable pageable) {

		Specification<Product> spec = Specification.where(null);

		if (keyword != null && !keyword.isEmpty()) {
			spec = spec.and((root, q, cb) -> cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));

		}
		return productRepository.findAll(spec, pageable);
	}

	public Product insert(ProductDto productDto, MultipartFile avatarFile, MultipartFile[] imageFiles)
			throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		Product product = new Product();
		product.setId(null); // Can than
		Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
		product.setCategory(category);

		product.setName(productDto.getName());
		product.setDetailDescription(productDto.getDetailDescription());
		product.setShortDescription(productDto.getShortDescription());
		product.setSeo(productDto.getSeo());
		product.setStatus(productDto.getStatus() == null ? product.getStatus() : true);
		product.setIsHot(productDto.getIsHot() == null ? product.getIsHot() : true);
		product.setCreateDate(productDto.getCreateDate());
		product.setUpdateDate(productDto.getUpdateDate());

		// Xu ly khoa ngoai cua quan he 1-n giua user-product
		if (productDto.getCreateById() != null && productDto.getCreateById() != 0) {
			User user = userRepository.findById(productDto.getCreateById()).orElse(null);
			product.setCreateBy(user);
		} else {
			product.setCreateBy(null);
		}

		if (productDto.getUpdateById() != null && productDto.getUpdateById() != 0) {
			User user = userRepository.findById(productDto.getUpdateById()).orElse(null);
			product.setUpdateBy(user);
		} else {
			product.setUpdateBy(null);
		}

		if (productDto.getPrice() != null) {
			product.setPrice(productDto.getPrice());
		} else {
			product.setPrice(BigDecimal.ZERO);

		}

		if (productDto.getSalePrice() != null) {
			product.setSalePrice(productDto.getSalePrice());
		} else {
			product.setSalePrice(BigDecimal.ZERO);
		}

		if (isExistFile(avatarFile)) { // Co upload avatar
			// Luu duong dan vao DB
			product.setAvatar("Product/Avatar/" + avatarFile.getOriginalFilename());

			// Luu file vao thu muc tren server
			String path = J35Constant.FOLDER_PATH + "Product/Avatar/" + avatarFile.getOriginalFilename();

			File file = new File(path);
			avatarFile.transferTo(file);
		} else { // Khong upload
			product.setAvatar(null);// FILE ANH NO IMAGE
		}

		if (isExistFiles(imageFiles)) {
			for (MultipartFile imageFile : imageFiles) {
				if (isExistFile(imageFile)) {
					String path = J35Constant.FOLDER_PATH + "Product/Avatar/" + imageFile.getOriginalFilename();
					File file = new File(path);
					imageFile.transferTo(file);

					// Luu thong tin anh vao bang tbl_product_image
					ProductImage productImage = new ProductImage();
					productImage.setId(null);
					productImage.setCreateDate(LocalDate.now());
					productImage.setTitle(imageFile.getOriginalFilename());
					// Luu duong dan vao db
					productImage.setPath("Product/Image/" + imageFile.getOriginalFilename());
					productImage.setStatus(Boolean.TRUE);

					// Xu ly quan he 1-n giua product - product image
					productImage.setProduct(product);
					// Luu doi tuong anh vao db: tbl_product_image
					product.addRelationalProductImage(productImage);
				}
			}
		}
		return productRepository.save(product);
	}

	public Boolean isExistFile(MultipartFile file) {
		if (file != null && file.getOriginalFilename() != null && file.getOriginalFilename().length() > 0) {
			return true;
		}
		return false;
	}

	public Boolean isExistFiles(MultipartFile[] files) {
		if (files != null && files.length > 0) {
			return true;
		}
		return false;
	}

	public Product update(ProductDto productDto, Integer id, MultipartFile avatarFile, MultipartFile[] imageFiles)
			throws IOException {
		// Lay product trong DB
		Product product = productRepository.findById(id).orElse(null);
		if (product == null) {
			return product;
		}
		// Set category; quan he category 1-n product
		if (productDto.getCategoryId() != null && productDto.getCategoryId() != 0) {
			Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
			product.setCategory(category);
		} else {
			product.setCategory(null);
		}

		product.setName(productDto.getName());
		product.setDetailDescription(productDto.getDetailDescription());
		product.setShortDescription(productDto.getShortDescription());

		product.setCreateDate(productDto.getCreateDate());
		product.setUpdateDate(productDto.getUpdateDate());

		if (productDto.getCreateById() != null && productDto.getCreateById() != 0) {
			User user = userRepository.findById(productDto.getCreateById()).orElse(null);
			product.setUpdateBy(user);
		} else {
			product.setUpdateBy(null);
		}

		if (productDto.getPrice() != null) {
			product.setPrice(productDto.getPrice());
		} else {
			product.setPrice(BigDecimal.ZERO);

		}

		if (productDto.getSalePrice() != null) {
			product.setSalePrice(productDto.getSalePrice());
		} else {
			product.setSalePrice(BigDecimal.ZERO);
		}

		product.setSeo(productDto.getSeo());
		product.setStatus(productDto.getStatus() != null ? productDto.getStatus() : Boolean.TRUE);
		product.setIsHot(productDto.getIsHot() != null ? productDto.getIsHot() : Boolean.FALSE);

		if (isExistFile(avatarFile)) {// Co uploade avatar file thi
			// Xoa avatar cu neu co
			if (product.getAvatar() != null) { // Xoa file trong thu muc Pr
				String path = J35Constant.FOLDER_PATH + product.getAvatar();
				File file = new File(path);
				file.delete();
			}
			// Luu (thay the) duong dan vao DB
			product.setAvatar("Product/Avatar/" + avatarFile.getOriginalFilename());
			// Luu file avatar moi vao thu muc Product/Avatar
			String path = J35Constant.FOLDER_PATH + "Product/Avatar/" + avatarFile.getOriginalFilename();
			File file = new File(path);
			avatarFile.transferTo(file);
		}

		if (isExistFiles(imageFiles)) {
			for (MultipartFile imageFile : imageFiles) {
				if (isExistFile(imageFile)) {
					String path = J35Constant.FOLDER_PATH + "Product/Avatar/" + imageFile.getOriginalFilename();
					File file = new File(path);
					imageFile.transferTo(file);

					// Luu thong tin anh vao bang tbl_product_image
					ProductImage productImage = new ProductImage();
					productImage.setId(null);
					productImage.setCreateDate(LocalDate.now());
					productImage.setTitle(imageFile.getOriginalFilename());
					// Luu duong dan vao db
					productImage.setPath("Product/Image/" + imageFile.getOriginalFilename());
					productImage.setStatus(Boolean.TRUE);

					// Xu ly quan he 1-n giua product - product image
					productImage.setProduct(product);
					// Luu doi tuong anh vao db: tbl_product_image
					product.addRelationalProductImage(productImage);
				}
			}
		}
		return productRepository.save(product);
	}

	public Boolean existsByNameAndIdNot(String name, Integer id) {
		// TODO Auto-generated method stub
		return productRepository.existsByNameAndIdNot(name, id);
	}

	public void inactive(Integer id) {
		// Lay du lieu trong DB
		Product product = findById(id);
		if (product == null) {
			return;
		}
		product.setStatus(false);
		productRepository.save(product);

		// Xoa hẳn
		// prodRepo.delete(product);

	}

}
