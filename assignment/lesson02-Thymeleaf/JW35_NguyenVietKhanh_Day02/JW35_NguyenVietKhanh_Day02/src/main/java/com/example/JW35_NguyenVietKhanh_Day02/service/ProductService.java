package com.example.JW35_NguyenVietKhanh_Day02.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.JW35_NguyenVietKhanh_Day02.dto.Constant;
import com.example.JW35_NguyenVietKhanh_Day02.dto.Db;
import com.example.JW35_NguyenVietKhanh_Day02.dto.Product;

@Service
public class ProductService implements Constant {

	public Product findById(int id) {
		for (Product product : Db.getProducts()) {
			if (product.getId() == id) {
				return product;
			}
		}
		return null;
	}

	public List<Product> findAll() {
		return Db.getProducts();
	}

	public boolean existsByName(String name) {
		// TODO Auto-generated method stub
		for (Product product : Db.getProducts()) {
			if (product.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public void save(Product product, MultipartFile productImageFile) {
		// TODO Auto-generated method stub
		product.setId(Db.getProductId());
		Db.setProductId(Db.getProductId() + 1);

		if (productImageFile != null && !productImageFile.isEmpty()) {
			String path = FOLDER_UPLOAD + "Product/" + productImageFile.getOriginalFilename();

			File file = new File(path);

			try {
				productImageFile.transferTo(file);
				product.setImage("Product/" + productImageFile.getOriginalFilename());
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Db.getProducts().add(product);
	}
}
