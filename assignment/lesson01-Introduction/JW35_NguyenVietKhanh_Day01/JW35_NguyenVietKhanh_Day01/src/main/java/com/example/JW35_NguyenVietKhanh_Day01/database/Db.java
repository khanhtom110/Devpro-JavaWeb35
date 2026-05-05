package com.example.JW35_NguyenVietKhanh_Day01.database;

import java.util.ArrayList;
import java.util.List;

import com.example.JW35_NguyenVietKhanh_Day01.entity.User;

public class Db {

	private static List<User> users = new ArrayList<User>() {
		{
			add(new User(1, "Giang Le", "Co", "0914583764", "Tu Liem, Ha Noi", "giangleco@gmail.com", "Nam"));
			add(new User(2, "Tran Thi Ngoc", "Trinh", "0914583764", "Tu Liem, Ha Noi", "trinh@gmail.com", "Nu"));
			add(new User(3, "Tran Dinh", "Trong", "0914583764", "Tu Liem, Ha Noi", "trong@gmail.com", "Nam"));
		}

	};

	public static List<User> getUsers() {
		return users;
	}

	public static void setUsers(List<User> users) {
		Db.users = users;
	}

}
