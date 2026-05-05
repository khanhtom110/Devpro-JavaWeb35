package com.example.JW35_NguyenVietKhanh_Day02.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Product {

	private int id;

	@NotBlank(message = "Tên không được để trống")
	private String name;

	private String image;

	@NotNull(message = "Trọng lượng không được để trống")
	private Double weight;

	@NotNull(message = "Số lượng không được để trống")
	@Positive(message = "Số lượng phải lớn hơn 0")
	private int quantity;

	@NotNull(message = "Đơn giá không được để trống")
	@Positive(message = "Đơn giá không thể âm")
	private Double price;

	@NotNull(message = "NSX không được để trống")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate manufactureDate;

	@NotNull(message = "HSD không được để trống")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expiryDate;

	@NotBlank(message = "Màu sắc không được để trống")
	private String color;

	private Boolean status = Boolean.TRUE;

	public Product() {
		super();
	}

	public Product(int id, String name, String image, Double weight, int quantity, Double price,
			LocalDate manufactureDate, LocalDate expiryDate, String color, Boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.weight = weight;
		this.quantity = quantity;
		this.price = price;
		this.manufactureDate = manufactureDate;
		this.expiryDate = expiryDate;
		this.color = color;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(LocalDate manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
