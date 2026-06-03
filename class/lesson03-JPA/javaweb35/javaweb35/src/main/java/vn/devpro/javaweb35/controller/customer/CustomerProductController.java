package vn.devpro.javaweb35.controller.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import vn.devpro.javaweb35.model.Product;
import vn.devpro.javaweb35.service.ProductService;

@Controller
public class CustomerProductController {
	private final ProductService productService;

	public CustomerProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping("/home")
	public String getHomePage(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "createDate") String column,
			@RequestParam(defaultValue = "desc") String direction, @RequestParam(required = false) String keyword) {

		Sort.Direction dir = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;

		PageRequest pageable = PageRequest.of(page, size, Sort.by(dir, column));

		Page<Product> productPage = productService.search(keyword, pageable);

		model.addAttribute("title", "Trang khách hàng");
		model.addAttribute("products", productPage.getContent());
		model.addAttribute("productPage", productPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("size", size);
		model.addAttribute("column", column);
		model.addAttribute("page", page);
		model.addAttribute("direction", direction);

		return "customer/index";
	}
}
