package vn.devpro.demo.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Db {
	private static int userId=1;
	
	private static List<User> users=new ArrayList<User>() {
		{
			add(new User(Db.userId++,"langlt","123","Le Thi","Lang",LocalDate.of(2000,3,15),"Nu","langlt@gmail.com","0912283846",30000000,"User/Avatar/avatar2.jpg",LocalDate.now(),"Tieng Anh",true));
			add(new User(Db.userId++,"noilt","123","Le Thi","Noi",LocalDate.of(2000,3,15),"Nu","noilt@gmail.com","0912283846",30000000,"User/Avatar/avatar.jpg",LocalDate.now(),"Tieng Thai",true));
			add(new User(Db.userId++,"kiendt","123","Do Trung","Kien",LocalDate.of(2000,3,15),"Nam","kiendt@gmail.com","0912283846",30000000,"User/Avatar/avatar2.jpg",LocalDate.now(),"Tieng Trung",true));
			add(new User(Db.userId++,"trinhttn","123","Tran Thi Ngoc","Trinh",LocalDate.of(2000,3,15),"kiendt","langlt@gmail.com","0912283846",30000000,"User/Avatar/avatar.jpg",LocalDate.now(),"Tieng Nhat",true));
			add(new User(Db.userId++,"khanhnv","123","Nguyen Viet","Khanh",LocalDate.of(2000,3,15),"Nam","khanhnv@gmail.com","0912283846",30000000,"User/Avatar/avatar2.jpg",LocalDate.now(),"Tieng Anh",true));
			add(new User(Db.userId++,"huebb","123","Bun Bo","Hue",LocalDate.of(2000,3,15),"Nu","huebb@gmail.com","0912283846",30000000,"User/Avatar/avatar.jpg",LocalDate.now(),"Tieng Thai",true));
		}
		
	};
	
	private static List<String> languagues = new ArrayList<String>() {
		{
			add("Tieng Viet");
			add("Tieng Anh");
			add("Tieng Trung");
			add("Tieng Nhat");
			add("Tieng Thai");
		}
	};
	

	public static int getUserId() {
		return userId;
	}

	public static void setUserId(int userId) {
		Db.userId = userId;
	}

	public static List<User> getUsers() {
		return users;
	}

	public static void setUsers(List<User> users) {
		Db.users = users;
	}

	public static List<String> getLanguagues() {
		return languagues;
	}

	public static void setLanguagues(List<String> languagues) {
		Db.languagues = languagues;
	}
	
}
