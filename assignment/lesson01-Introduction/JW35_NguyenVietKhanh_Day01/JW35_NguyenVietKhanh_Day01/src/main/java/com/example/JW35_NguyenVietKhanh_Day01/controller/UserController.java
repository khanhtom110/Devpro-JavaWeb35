package com.example.JW35_NguyenVietKhanh_Day01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.JW35_NguyenVietKhanh_Day01.entity.User;
import com.example.JW35_NguyenVietKhanh_Day01.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public String view(Model model) {

		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "user/user";
	}
}
