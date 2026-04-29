package vn.devpro.demo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class User {
	private int id;

	@NotBlank(message = "Tài khoản không được để trống")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Tài khoản chỉ được chứa chữ cái và chữ số")
	private String username;

	private String password;

	@NotBlank(message = "Họ đệm không được để trống")
	private String firstname;

	@NotBlank(message = "Tên không được để trống")
	private String lastname;

	@NotNull(message = "Ngày sinh không được để trống")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	@NotBlank(message = "Giới tính chưa được chọn")
	private String gender;

	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email chưa đúng định dạng")
	private String email;

	@NotBlank(message = "SĐT không được để trống")
	@Pattern(regexp = "^[0-9]{10}$", message = "SĐT gồm 10 chữ số")
	private String mobile;

	@DecimalMin(value = "3000000", message = "Lương phải lớn hơn 3.000.000")
	private double salary;

	private String avatar;

	@NotNull(message = "Ngày tạo không được để trống")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createDate;

	@NotBlank(message = "Ngôn ngữ không được để trống")
	private String language;

	private Boolean status = Boolean.TRUE;

	public User() {
		super();
	}

	public User(int id, String username, String password, String firstname, String lastname, LocalDate dateOfBirth,
			String gender, String email, String mobile, double salary, String avatar, LocalDate createDate,
			String language, Boolean status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.salary = salary;
		this.avatar = avatar;
		this.createDate = createDate;
		this.language = language;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
