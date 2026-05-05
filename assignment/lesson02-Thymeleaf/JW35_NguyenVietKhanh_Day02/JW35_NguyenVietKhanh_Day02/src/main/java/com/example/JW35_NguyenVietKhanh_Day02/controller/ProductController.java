package com.example.JW35_NguyenVietKhanh_Day02.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.JW35_NguyenVietKhanh_Day02.dto.Db;
import com.example.JW35_NguyenVietKhanh_Day02.dto.Product;
import com.example.JW35_NguyenVietKhanh_Day02.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping
	public String home(Model model) {

		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "/product/product";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);

		List<Double> weights = Db.getWeights();
		model.addAttribute("weights", weights);

		return "/product/new-product";
	}

	@PostMapping("/add/save")
	public String save(@Valid @ModelAttribute("product") Product product, BindingResult result,
			@RequestParam("productImageFile") MultipartFile productImageFile, Model model) {
		if (productService.existsByName(product.getName())) {
			result.rejectValue("name", "error.product", "Ten san pham da ton tai");
		}

		if (result.hasErrors()) {
			List<Double> weights = Db.getWeights();
			model.addAttribute("weights", weights);
			model.addAttribute("product", product);
			return "/product/new-product";
		}

		productService.save(product, productImageFile);

		return "redirect:/product";
	}
}
