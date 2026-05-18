package vn.devpro.javaweb35.controller.administrator;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
