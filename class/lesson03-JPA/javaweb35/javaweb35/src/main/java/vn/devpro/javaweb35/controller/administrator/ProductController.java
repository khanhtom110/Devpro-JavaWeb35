package vn.devpro.javaweb35.controller.administrator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import vn.devpro.javaweb35.service.ProductService;

@Controller
@RequestMapping("/staff/product")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	public String home(Model model, HttpServletRequest request,

			// Phan trang
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,

			// Sap xep
			@RequestParam(defaultValue = "name") String column, @RequestParam(defaultValue = "asc") String direction,

			// Tim kiem
			@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) String status, @RequestParam(required = false) BigDecimal fromPrice,
			@RequestParam(required = false) BigDecimal toPrice,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {

		Boolean statusBool = null; // Khong chon gi ca
		if ("true".equalsIgnoreCase(status)) {
			statusBool = true;
		}
		if ("false".equalsIgnoreCase(status)) {
			statusBool = false;
		}

		// Dam bao from <= to
		if (fromPrice != null && toPrice != null && fromPrice.compareTo(toPrice) > 0) {
			BigDecimal t = fromPrice;
			fromPrice = toPrice;
			toPrice = t;
		}

		LocalDateTime fromInclusive = null;
		LocalDateTime toInclusive = null;

		if (toDate != null) {
			toInclusive = toDate.atTime(LocalTime.MAX);
		}
		if (fromDate != null) {
			fromInclusive = fromDate.atStartOfDay();
		}

		// Sap xep
		Sort.Direction dir = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		// Phan trang
//	    PageRequest pageable = 
//	            PageRequest.of(page, size, Sort.by(dir, column));
//	    Page<Product> productsPage = productService.search(
//	            keyword,
//	            categoryId == null ? 0 : categoryId,
//	            statusBool,
//	            fromPrice, toPrice,
//	            fromInclusive,
//	            toInclusive,pageable
//	    };
//
//	model.addAttribute("products",productsPage.getContent());model.addAttribute("productsPage",productsPage);
//
//	model.addAttribute("currentPage",page);model.addAttribute("pageSize",size);model.addAttribute("column",column);model.addAttribute("direction",direction);
//
//	model.addAttribute("categoryId",categoryId);model.addAttribute("keyword",keyword);model.addAttribute("fromDate",fromDate);model.addAttribute("toDate",toDate);model.addAttribute("status",status);
//
//	model.addAttribute("fromPrice",fromPrice);model.addAttribute("toPrice",toPrice);
//
//	model.addAttribute("categories",cs.findAllActive());

		return "administrator/product/product";
	}

}
