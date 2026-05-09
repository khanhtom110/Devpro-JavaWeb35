package com.example.JW35_NguyenVietKhanh_Day02;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.JW35_NguyenVietKhanh_Day02.entity.Product;

public class Db {

	private static int productId = 1;

	private static List<Product> products = new ArrayList<Product>() {
		{
			add(new Product(productId++, "Chuot", "Product/chuot.jpg", 200.0, 10, 100000.0, LocalDate.of(2025, 1, 1),
					LocalDate.of(2026, 1, 1), "White", true));
			add(new Product(productId++, "Ban phim", "Product/banphim.jpg", 500.0, 10, 100000.0,
					LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), "Red", true));
			add(new Product(productId++, "Pad chuot", "Product/padchuot.jpg", 200.0, 10, 100000.0,
					LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), "Blue", true));
			add(new Product(productId++, "Ke do may tinh", "Product/kemaytinh.jpg", 750.0, 10, 100000.0,
					LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), "Green", true));
			add(new Product(productId++, "Bao dung laptop", "Product/baolaptop.jpg", 200.0, 10, 100000.0,
					LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), "Yellow", true));
		}
	};

	private static List<Double> weights = new ArrayList<Double>() {
		{
			add(200.0);
			add(500.0);
			add(750.0);
			add(1000.0);
		}
	};

	public static int getProductId() {
		return productId;
	}

	public static void setProductId(int productId) {
		Db.productId = productId;
	}

	public static List<Product> getProducts() {
		return products;
	}

	public static void setProducts(List<Product> products) {
		Db.products = products;
	}

	public static List<Double> getWeights() {
		return weights;
	}

	public static void setWeights(List<Double> weights) {
		Db.weights = weights;
	}

}
