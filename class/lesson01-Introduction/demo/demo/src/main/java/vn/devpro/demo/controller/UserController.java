package vn.devpro.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.devpro.demo.dto.User;

@Controller
@RequestMapping("/users")

public class UserController {
	@GetMapping({"/",""})
	public String view(final Model model) {
		
		List<User> users=new ArrayList<User>();
		users.add(new User(1, "admin", "admin", "Nguyen", "Anh", LocalDate.of(2005, 11, 20), "Nam", "abc@gmail.com", "0928837465", 25000000, "User/Avatar/avatar.jpg", LocalDate.now(), "English", true));
		users.add(new User(2, "user", "user", "Nguyen", "Binh", LocalDate.of(2005, 11, 20), "Nam", "abc@gmail.com", "0928837465", 25000000, "User/Avatar/avatar2.jpg", LocalDate.now(), "English", true));
		
		
		model.addAttribute("users", users);
		return"user/user";
	}
}