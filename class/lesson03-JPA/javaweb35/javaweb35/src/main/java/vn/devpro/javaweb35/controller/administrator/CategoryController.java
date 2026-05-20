package vn.devpro.javaweb35.controller.administrator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import vn.devpro.javaweb35.dto.CategoryDto;
import vn.devpro.javaweb35.model.Category;
import vn.devpro.javaweb35.model.User;
import vn.devpro.javaweb35.service.CategoryService;
import vn.devpro.javaweb35.service.UserService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	private final CategoryService categoryService;
	private final UserService userService;

	public CategoryController(CategoryService categoryService, UserService userService) {
		super();
		this.categoryService = categoryService;
		this.userService = userService;
	}

	@GetMapping
	public String read(Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		return "administrator/category/category";
	}

//	public String viewAndSortByName(Model model) {
//		List<Category> categories = categoryService.findAllAndSortByName();
//
//		model.addAttribute("categories", categories);
//		return "administrator/category/category";
//	}

//	public String viewAllAndSort(@RequestParam(required = false) String column,
//			@RequestParam(defaultValue = "asc") String direction, Model model) {
//		List<Category> categories = categoryService.findAllAndSort(column, direction);
//		model.addAttribute("direction", direction);
//		model.addAttribute("column", column);
//		model.addAttribute("categories", categories);
//		return "administrator/category/category";
//	}

//	public String viewAllSortAndPagniation(@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "2") int size,
//			@RequestParam(required = false, defaultValue = "name") String column,
//			@RequestParam(defaultValue = "asc") String direction, Model model) {
//		if (column == null || column.isBlank()) {
//			column = "name";
//		}
//		Sort.Direction dir = ("asc".equalsIgnoreCase(direction)) ? Sort.Direction.ASC : Sort.Direction.DESC;
//
//		PageRequest pageable = PageRequest.of(page, size, Sort.by(dir, column));
//		Page<Category> categoriesPage = categoryService.findAllSortAndPageable(pageable);
//
//		model.addAttribute("categoriesPage", categoriesPage);
//		model.addAttribute("categories", categoriesPage.getContent());
//
//		model.addAttribute(column, column);
//		model.addAttribute(direction, direction);
//
//		model.addAttribute("currentPage", page);
//		model.addAttribute("pageSize", size);
//
//		return "administrator/category/category";
//	}

//	public String home(Model model, @RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "3") int size,
//			@RequestParam(required = false, defaultValue = "name") String column,
//			@RequestParam(defaultValue = "asc") String direction, @RequestParam(required = false) String name,
//			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
//			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
//
//		Sort.Direction dir = ("asc".equalsIgnoreCase(direction)) ? Sort.Direction.ASC : Sort.Direction.DESC;
//
//		PageRequest pageable = PageRequest.of(page, size, Sort.by(dir, column));
//		LocalDateTime fromInclusive = null;
//		LocalDateTime toInclusive = null;
//
//		if (toDate != null) {
//			toInclusive = toDate.atTime(LocalTime.MAX);
//		}
//		if (fromDate != null) {
//			toInclusive = toDate.atStartOfDay();
//		}
//		Page<Category> categoriesPage = categoryService.findAllSortPageableAndSearch(name, fromInclusive, toInclusive,
//				pageable);
//
//		model.addAttribute("categoriesPage", categoriesPage);
//		model.addAttribute("categories", categoriesPage.getContent());
//
//		model.addAttribute(column, column);
//		model.addAttribute(direction, direction);
//
//		model.addAttribute("currentPage", page);
//		model.addAttribute("pageSize", size);
//
//		model.addAttribute("name", name);
//		model.addAttribute("fromDate", fromDate);
//		model.addAttribute("toDate", toDate);
//
//		return "administrator/category/category";
//	}

	@GetMapping("/add")
	public String add(Model model) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCreateDate(LocalDate.now());
		model.addAttribute("categoryDto", categoryDto);

		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);

		List<User> users = userService.findAll();
		model.addAttribute("users", users);

		return "administrator/category/add";
	}

	@PostMapping("/add/save")
	public String saveNewCategory(final Model model, @Valid @ModelAttribute CategoryDto categoryDto,
			BindingResult bindingResult) {
		if (categoryService.existsByName(categoryDto.getName())) {
			bindingResult.rejectValue("name", null, "Ten da su dung, vui long chon ten khac");
		}

		if (categoryDto.getCreateDate() == null) {
			bindingResult.rejectValue("creatDate", null, "Chua chon ngay tao, vui long chon");
		}

		if (categoryDto.getCreateById() == null) {
			bindingResult.rejectValue("createById", null, "Chua chon nguoi tao, vui long chon");
		}

		if (bindingResult.hasErrors()) {
			List<User> users = userService.findAll();
			List<Category> categories = categoryService.findAll();
			categoryDto.setCreateDate(LocalDate.now());

			model.addAttribute("users", users);
			model.addAttribute("categories", categories);
			model.addAttribute("categoryDto", categoryDto);

		}

		categoryDto.setUpdateDate(null);
		categoryDto.setUpdateById(null);

		try {
			categoryService.insert(categoryDto);
		} catch (Exception e) {
			// TODO: handle exception
			List<User> users = userService.findAll();
			List<Category> categories = categoryService.findAll();
			categoryDto.setCreateDate(LocalDate.now());

			model.addAttribute("users", users);
			model.addAttribute("categories", categories);
			model.addAttribute("categoryDto", categoryDto);

			return "administrator/category/add";
		}

		return "redirect:/admin/category/add";
	}

	@GetMapping("/edit")
	public String editCategory(final Model model, @RequestParam int id) {
		Category category = categoryService.findById(id);

		if (category == null) {
			return "redirect:/admin/category";
		}

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setName(category.getName());

		if (category.getParentCategory() != null) {
			categoryDto.setCategoryId(category.getParentCategory().getId());
		}

		if (category.getCreateBy() != null) {
			categoryDto.setCreateById(category.getCreateBy().getId());
		}

		if (category.getUpdateBy() != null) {
			categoryDto.setUpdateById(category.getUpdateBy().getId());
		}

		categoryDto.setCreateDate(category.getCreateDate());
		categoryDto.setUpdateDate(LocalDate.now());
		categoryDto.setDescription(category.getDescription());
		categoryDto.setStatus(category.getStatus());

		model.addAttribute("categoryDto", categoryDto);
		model.addAttribute("category", category);

		List<User> users = userService.findAll();
		List<Category> categories = categoryService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("categories", categories);

		return "administrator/category/edit";
	}

	@PostMapping("/edit/save")
	public String editCategorySave(Model model, @Valid @ModelAttribute CategoryDto categoryDto,
			@RequestParam Integer id, BindingResult result) {
		Category category = categoryService.findById(id);

		if (category == null) {
			return "redirect:/admin/category";
		}

		// Cha va con khong duoc trung nhau
		if (categoryDto.getCategoryId() != null && categoryDto.getCategoryId() != 0) {
			Category parent = categoryService.findById(categoryDto.getCategoryId());
			if (parent != null && categoryDto.getName().equalsIgnoreCase(parent.getName())) {
				result.rejectValue("categoryId", null, "Ten cua cha phai khac ten cua con");
			}
		}

		// Sua ten trung voi ten khac
		if (!categoryDto.getName().equalsIgnoreCase(category.getName())
				&& categoryService.existsByName(categoryDto.getName())) {
			result.rejectValue("name", null, "Ten da duoc su dung");
		}

		if (categoryDto.getCreateDate() == null) {
			result.rejectValue("createDate", null, "Chua chon ngay tao, vui long chon");
		}

		if (categoryDto.getUpdateDate() == null) {
			result.rejectValue("updateDate", null, "Chua chon ngay sua, vui long chon");
		}

		if (categoryDto.getCreateById() == null) {
			result.rejectValue("createById", null, "Chua chon nguoi tao, vui long chon");
		}

		if (categoryDto.getUpdateById() == null) {
			result.rejectValue("updateById", null, "Chua chon nguoi sua, vui long chon");
		}

		if (result.hasErrors()) {
			List<User> users = userService.findAll();
			List<Category> categories = categoryService.findAll();

			categoryDto.setUpdateDate(LocalDate.now());

			model.addAttribute("users", users);
			model.addAttribute("categories", categories);
			model.addAttribute("categoryDto", categoryDto);
			model.addAttribute("category", category);

			return "administrator/category/edit";
		}

		try {
			categoryService.update(id, categoryDto);
		} catch (Exception e) {
			// TODO: handle exception
			List<User> users = userService.findAll();
			List<Category> categories = categoryService.findAll();

			categoryDto.setUpdateDate(LocalDate.now());

			model.addAttribute("users", users);
			model.addAttribute("categories", categories);
			model.addAttribute("categoryDto", categoryDto);
			model.addAttribute("saveError", true);

			return "administrator/category/edit";
		}

		return "redirect:/admin/category";
	}

	@GetMapping("/delete")
	public String deleteCategory(@RequestParam Integer id) {
		if (id != null && id > 0) {
			categoryService.inactive(id);
		}
		return "redirect:/admin/category";
	}
}
