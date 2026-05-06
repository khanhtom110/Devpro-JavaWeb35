package vn.devpro.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.devpro.demo.dto.Constant;
import vn.devpro.demo.dto.Db;
import vn.devpro.demo.dto.User;

@Service
public class UserService implements Constant {
	public User findById(int id) {
		for (User user : Db.getUsers()) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public List<User> findAll() {
		return Db.getUsers();
	}

	public List<String> findLanguages() {
		return Db.getLanguagues();
	}

	public boolean existsByUserName(String userName) {
		// TODO Auto-generated method stub
		for (User user : Db.getUsers()) {
			if (user.getUsername().trim().equalsIgnoreCase(userName.trim())) {
				return true;
			}
		}
		return false;
	}

	public void save(User user, MultipartFile avatarFile) {
		// TODO Auto-generated method stub

		user.setId(Db.getUserId());
		Db.setUserId(Db.getUserId() + 1);

		if (avatarFile != null && !avatarFile.getOriginalFilename().isEmpty()) {
			String path = FOLDER_UPLOAD + "User/Avatar/" + avatarFile.getOriginalFilename();

			File file = new File(path);
			try {
				avatarFile.transferTo(file);
				user.setAvatar("User/Avatar/" + avatarFile.getOriginalFilename());
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Db.getUsers().add(user);
	}

	// Kiem tra username da update khac voi user khac ngoai tru minh
	public boolean existsByUsernameExceptId(String username, int id) {
		// TODO Auto-generated method stub
		for (User user : Db.getUsers()) {
			if (user.getId() != id && user.getUsername().trim().equalsIgnoreCase(username.trim())) {
				return true;
			}
		}
		return false;
	}

	public boolean existsByEmailExceptId(String email, int id) {
		// TODO Auto-generated method stub
		for (User user : Db.getUsers()) {
			if (user.getId() != id && user.getEmail().trim().equalsIgnoreCase(email.trim())) {
				return true;
			}
		}
		return false;
	}

	public void update(User user, MultipartFile avatarFile) {
		// TODO Auto-generated method stub

		User userDb = findById(user.getId());

		if (avatarFile != null && !avatarFile.getOriginalFilename().isEmpty()) {

			// Xoa avatar cu

			String path = FOLDER_UPLOAD + userDb.getAvatar();
			File file = new File(path);
			file.delete();

			path = FOLDER_UPLOAD + "User/Avatar" + avatarFile.getOriginalFilename();

			file = new File(path);

			file.getParentFile().mkdirs();
			try {
				avatarFile.transferTo(file);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setAvatar("User/Avatar" + avatarFile.getOriginalFilename());
		} else {
			user.setAvatar(userDb.getAvatar());
		}

		int index = Db.getUsers().indexOf(userDb);
		Db.getUsers().set(index, user);
	}

	public void delete(User user) {
		// TODO Auto-generated method stub

		// Tro vao vi tri trong db
		int index = Db.getUsers().indexOf(user);

		// Cach 1: Xoa luon
//		Db.getUsers().remove(index);

		// Cach 2: An du lieu
		Db.getUsers().get(index).setStatus(Boolean.FALSE);
	}

}
