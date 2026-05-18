package vn.devpro.javaweb35.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
	@NotBlank(message = "Ten khong de trong")
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate updateDate;

	private String description;

	private Boolean status = Boolean.TRUE;

	private Integer categoryId;

	private Integer createById;

	private Integer updateById;

	public CategoryDto() {
		super();
	}

	public CategoryDto(@NotBlank(message = "Ten khong de trong") String name, LocalDate createDate,
			LocalDate updateDate, String description, Boolean status, Integer categoryId, Integer createById,
			Integer updateById) {
		super();
		this.name = name;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.description = description;
		this.status = status;
		this.categoryId = categoryId;
		this.createById = createById;
		this.updateById = updateById;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCreateById() {
		return createById;
	}

	public void setCreateById(Integer createById) {
		this.createById = createById;
	}

	public Integer getUpdateById() {
		return updateById;
	}

	public void setUpdateById(Integer updateById) {
		this.updateById = updateById;
	}

}
