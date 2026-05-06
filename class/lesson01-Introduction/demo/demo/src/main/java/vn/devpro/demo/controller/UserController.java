package vn.devpro.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.devpro.demo.dto.User;
import vn.devpro.demo.service.UserService;

@Controller
@RequestMapping("/user")

public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public String view(final Model model) {

		List<User> users = userService.findAll();

		model.addAttribute("users", users);
		return "user/user";
	}

	@GetMapping("/add")
	public String addUser(final Model model) {
		User user = new User();
		model.addAttribute("user", user);

		List<String> languages = userService.findLanguages();
		model.addAttribute("languages", languages);

		return "user/new-user";
	}

	@PostMapping("add/save")
	public String addSave(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam("avatarFile") MultipartFile avatarFile, Model model) {
		if (userService.existsByUserName(user.getUsername())) {
			result.rejectValue("username", "error.user", "Tai khoan da ton tai");
		}

		if (user.getPassword() == null || user.getPassword().isBlank()) {
			result.rejectValue("password", "error.user", "Mat khau khong duoc de trong");
		}

		String regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{6,}$";
		if (!user.getPassword().matches(regexp)) {
			result.rejectValue("password", "error.user",
					"Mat khau phai co chu hoa, chu thuong, chu so, ky tu dac biet, toi thieu 6 ky tu");
		}

		if (result.hasErrors()) {
			List<String> languages = userService.findLanguages();
			model.addAttribute("languages", languages);
			model.addAttribute("user", user);
			return "user/new-user";
		}

		userService.save(user, avatarFile);

		return "redirect:/user";
	}

	@GetMapping("/edit")
	public String editView(@RequestParam("id") int id, Model model) {

		User user = userService.findById(id);
		model.addAttribute("user", user);

		if (user == null) {
			model.addAttribute("users", userService.findAll());
			return "user/user";
		}

		List<String> languages = userService.findLanguages();
		model.addAttribute("languages", languages);

		return "user/edit-user";
	}

	@PostMapping("edit/save")
	public String editSave(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			@RequestParam("avatarFile") MultipartFile avatarFile) {

		User userDb = userService.findById(user.getId());

		if (user == null) {
			model.addAttribute("users", userService.findAll());
			return "user/user";
		}

		if (userService.existsByUsernameExceptId(user.getUsername(), user.getId())) {
			bindingResult.rejectValue("username", "error.user", "Tai khoan da ton tai, vui long chon ten khac");
		}

		if (user.getPassword() == null || user.getPassword().isBlank()) {
			bindingResult.rejectValue("password", "error.user", "Mat khau khong duoc de trong");
		}

		String regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{6,}$";
		if (!user.getPassword().matches(regexp)) {
			bindingResult.rejectValue("password", "error.user",
					"Mat khau phai co chu hoa, chu thuong, chu so, ky tu dac biet, toi thieu 6 ky tu");
		}

		if (userService.existsByEmailExceptId(user.getEmail(), user.getId())) {
			bindingResult.rejectValue("email", "error.user", "Email da ton tai, vui long chon email khac");
		}

		if (bindingResult.hasErrors()) {
			List<String> languages = userService.findLanguages();
			model.addAttribute("languages", languages);
			model.addAttribute("user", user);
			return "user/edit-user";
		}

		userService.update(user, avatarFile);

		return "redirect:/user";
	}

	@GetMapping("/delete")
	public String delete(Model model, @RequestParam("id") int id) {

		User user = userService.findById(id);

		if (user != null) {
			userService.delete(user);
		}

		List<User> users = userService.findAll();

		model.addAttribute("users", users);
		return "user/user";
	}
}