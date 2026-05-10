package vn.devpro.javaweb35.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "firstname", nullable = true)
	private String firstname;

	@Column(name = "lastname", nullable = true)
	private String lastname;

	@Column(name = "avatar", nullable = true, length = 255)
	private String avatar;

	@Column(name = "mobile", nullable = true, length = 50)
	private String mobile;

	@Column(name = "emai", nullable = true, length = 120)
	private String email;

	@Column(name = "address", nullable = true, length = 300)
	private String address;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "create_date", nullable = true)
	private LocalDate createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "update_date", nullable = true)
	private LocalDate updateDate;

	@Column(name = "status")
	private Boolean status = Boolean.TRUE;

	@Column(name = "description", nullable = true)
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	public void addRelationalUserRole(Role role) {
		roles.add(role);
		role.getUsers().add(this);
	}
	
	public void deleteRelationalRole(Role role) {
		roles.remove(role);
		role.getUsers().remove(this);
	}

}
