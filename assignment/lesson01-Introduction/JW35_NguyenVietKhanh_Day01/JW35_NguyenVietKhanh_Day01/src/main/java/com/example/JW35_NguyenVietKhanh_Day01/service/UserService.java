package com.example.JW35_NguyenVietKhanh_Day01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.JW35_NguyenVietKhanh_Day01.database.Db;
import com.example.JW35_NguyenVietKhanh_Day01.entity.User;

@Service
public class UserService {

	public List<User> findAll() {
		return Db.getUsers();
	}
}
