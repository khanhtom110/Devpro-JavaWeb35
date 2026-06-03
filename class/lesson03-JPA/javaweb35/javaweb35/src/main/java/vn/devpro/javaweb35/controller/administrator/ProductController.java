package vn.devpro.javaweb35.controller.administrator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import vn.devpro.javaweb35.dto.ProductDto;
import vn.devpro.javaweb35.model.Category;
import vn.devpro.javaweb35.model.Product;
import vn.devpro.javaweb35.model.User;
import vn.devpro.javaweb35.service.CategoryService;
import vn.devpro.javaweb35.service.ProductService;
import vn.devpro.javaweb35.service.UserService;

@Controller
@RequestMapping("/staff/product")
public class ProductController {
	private final ProductService productService;
	private final CategoryService categoryService;
	private final UserService userService;

	public ProductController(ProductService productService, CategoryService categoryService, UserService userService) {
		super();
		this.productService = productService;
		this.categoryService = categoryService;
		this.userService = userService;
	}

	@GetMapping
	public String home(Model model, HttpServletRequest request,

			// Phan trang
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,

			// Sap xep
			@RequestParam(defaultValue = "name") String column, @RequestParam(defaultValue = "asc") String direction,

			// Tim kiem
			@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) String status, @RequestParam(required = false) BigDecimal fromPrice,
			@RequestParam(required = false) BigDecimal toPrice,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {

		Boolean statusBool = null; // Khong chon gi ca
		if ("true".equalsIgnoreCase(status)) {
			statusBool = true;
		}
		if ("false".equalsIgnoreCase(status)) {
			statusBool = false;
		}

		// Dam bao from <= to
		if (fromPrice != null && toPrice != null && fromPrice.compareTo(toPrice) > 0) {
			BigDecimal t = fromPrice;
			fromPrice = toPrice;
			toPrice = t;
		}

		LocalDateTime fromInclusive = null;
		LocalDateTime toInclusive = null;

		if (toDate != null) {
			toInclusive = toDate.atTime(LocalTime.MAX);
		}
		if (fromDate != null) {
			fromInclusive = fromDate.atStartOfDay();
		}

		// Sap xep
		Sort.Direction dir = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		// Phan trang
		PageRequest pageable = PageRequest.of(page, size, Sort.by(dir, column));

		Page<Product> productsPage = productService.search(keyword, categoryId == null ? 0 : categoryId, statusBool,
				fromPrice, toPrice, fromInclusive, toInclusive, pageable);

		model.addAttribute("products", productsPage.getContent());
		model.addAttribute("productsPage", productsPage);

		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("column", column);
		model.addAttribute("direction", direction);

		model.addAttribute("categoryId", categoryId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("status", status);

		model.addAttribute("fromPrice", fromPrice);
		model.addAttribute("toPrice", toPrice);

		model.addAttribute("categories", categoryService.findAllActive());

		return "administrator/product/product";
	}

	@GetMapping("/add")
	public String addProduct(final Model model) {
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto", productDto);

		productDto.setCreateDate(LocalDate.now());

		List<User> users = userService.findAll();
		List<Category> categories = categoryService.findAll();

		model.addAttribute("users", users);
		model.addAttribute("categories", categories);

		return "administrator/product/add";
	}

	@PostMapping("/add/save")
	public String saveAddProduct(Model model, @Valid @ModelAttribute ProductDto productDto, BindingResult result,
			@RequestParam MultipartFile avatarFile, @RequestParam MultipartFile[] imageFiles) {

		if (productDto.getCategoryId() == null || productDto.getCategoryId() == 0) {
			result.rejectValue("categoryId", null, "Chưa chọn danh mục cho sản phẩm");
		}

		if (productService.existsByName(productDto.getName())) {
			result.rejectValue("name", null, "Tên sản phẩm đã sử dụng");

		}

		if (productDto.getCreateDate() == null) {
			result.rejectValue("createDate", null, "Chua chon ngay tao, vui long chon");
		}

//		if (productDto.getUpdateDate() == null) {
//			result.rejectValue("updateDate", null, "Chua chon ngay sua, vui long chon");
//		}

		if (productDto.getCreateById() == null) {
			result.rejectValue("createById", null, "Chua chon nguoi tao, vui long chon");
		}
//
//		if (productDto.getUpdateById() == null) {
//			result.rejectValue("updateById", null, "Chua chon nguoi sua, vui long chon");
//		}

		if (result.hasErrors()) {
			List<User> users = userService.findAll();
			List<Category> categories = categoryService.findAllActive();

			productDto.setCreateDate(LocalDate.now());

			model.addAttribute("productDto", productDto);
			model.addAttribute("categories", categories);
			model.addAttribute("users", users);

			model.addAttribute("saveError", false);
			return "administrator/product/add";
		}

		productDto.setUpdateDate(null);
		productDto.setUpdateById(null);

		try {
			productService.insert(productDto, avatarFile, imageFiles);
		} catch (Exception e) {
			e.printStackTrace();
			List<User> users = userService.findAll();
			List<Category> categories = categoryService.findAllActive();
			productDto.setCreateDate(LocalDate.now());
			model.addAttribute("productDto", productDto);
			model.addAttribute("categories", categories);
			model.addAttribute("users", users);

			model.addAttribute("saveError", false);
			return "administrator/product/add";
		}
		return "redirect:/staff/product/add";

	}

	@GetMapping("/edit")
	public String editProduct(Model model, @RequestParam int id) {
		// Lay product từ DB
		Product product = productService.findById(id);

		if (product == null) {
			return "redirect:/admin/product";
		}

		ProductDto productDto = new ProductDto();

		productDto.setName(product.getName());

		if (product.getCreateBy() != null) {
			productDto.setCreateById(product.getCreateBy().getId());
		}
		if (product.getUpdateBy() != null) {
			productDto.setUpdateById(product.getUpdateBy().getId());
		}
		productDto.setCreateDate(product.getCreateDate());
		productDto.setUpdateDate(LocalDate.now());

		productDto.setCategoryId(product.getCategory().getId());
		productDto.setShortDescription(product.getShortDescription());
		productDto.setDetailDescription(product.getDetailDescription());
		productDto.setStatus(product.getStatus());

		productDto.setPrice(product.getPrice());
		productDto.setSalePrice(product.getSalePrice());
		productDto.setIsHot(product.getIsHot());
		productDto.setSeo(product.getSeo());

		List<User> users = userService.findAll();
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("users", users);
		model.addAttribute("productDto", productDto);
		model.addAttribute("productId", product.getId());

		return "administrator/product/edit";
	}

	@PostMapping("/edit/save")
	public String saveEditProduct(Model model, @Valid @ModelAttribute ProductDto productDto, BindingResult result,
			@RequestParam(value = "id", required = false) Integer id, @RequestParam MultipartFile avatarFile,
			@RequestParam MultipartFile[] imageFiles) {

		if (id == null || id < 1) {
			return "redirect:/staff/product";
		}

		Product product = new Product();
		product.setId(id);

		if (productDto.getCategoryId() == null || productDto.getCategoryId() == 0) {
			result.rejectValue("categoryId", null, "Chua chon danh muc cho san pham");
		}

		if (productService.existsByNameAndIdNot(productDto.getName(), id)) {
			result.rejectValue("name", null, "Tên sản phẩm đã sử dụng");

		}

		if (productDto.getCreateDate() == null) {
			result.rejectValue("createDate", null, "Chua chon ngay tao, vui long chon");
		}

		if (productDto.getUpdateDate() == null) {
			result.rejectValue("updateDate", null, "Chua chon ngay sua, vui long chon");
		}

		if (productDto.getCreateById() == null) {
			result.rejectValue("createById", null, "Chua chon nguoi tao, vui long chon");
		}

		if (productDto.getUpdateById() == null) {
			result.rejectValue("updateById", null, "Chua chon nguoi sua, vui long chon");
		}

		if (result.hasErrors()) {
			List<User> users = userService.findAll();
			List<Category> categories = categoryService.findAllActive();

			productDto.setCreateDate(LocalDate.now());

			model.addAttribute("productDto", productDto);
			model.addAttribute("categories", categories);
			model.addAttribute("users", users);
			model.addAttribute("productId", id);

			model.addAttribute("saveError", false);
			return "administrator/product/edit";
		}

		try {
			productService.update(productDto, id, avatarFile, imageFiles);
		} catch (Exception e) {
			e.printStackTrace();
			List<User> users = userService.findAll();

			List<Category> categories = categoryService.findAllActive();
			model.addAttribute("productDto", productDto);
			model.addAttribute("categories", categories);
			model.addAttribute("users", users);
			model.addAttribute("product", product);
			model.addAttribute("productId", id);

			model.addAttribute("saveError", false);
			return "administrator/product/edit";
		}

		return "redirect:/staff/product";

	}

	@GetMapping("/delete")
	public String deleteProduct(@RequestParam Integer id) {
		if (id != null && id > 0) {
			productService.inactive(id);
		}
		return "redirect:/staff/product";

	}
}
