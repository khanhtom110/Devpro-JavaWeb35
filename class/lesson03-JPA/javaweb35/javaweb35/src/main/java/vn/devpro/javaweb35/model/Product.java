package vn.devpro.javaweb35.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false, unique = true, length = 500)
	private String name;

	@Column(name = "avatar", nullable = true, length = 255)
	private String avatar;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "sale_price")
	private BigDecimal salePrice;

	@Column(name = "is_hot")
	private Boolean isHot = Boolean.FALSE;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "create_date", nullable = true)
	private LocalDate createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "update_date", nullable = true)
	private LocalDate updateDate;

	@Column(name = "short_description", nullable = true, length = 255)
	private String shortDescription;

	@Column(name = "detail_description", nullable = true, length = 1000)
	private String detailDescription;

	@Column(name = "seo", nullable = true, length = 1000)
	private String seo;

	@Column(name = "status")
	private Boolean status = Boolean.TRUE;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "create_by", referencedColumnName = "id")
	private User createBy;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "update_by", referencedColumnName = "id")
	private User updateBy;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
	private List<ProductImage> productImages = new ArrayList<ProductImage>();

	public void addRelationalProductImage(ProductImage productImage) {
		productImages.add(productImage);
		productImage.setProduct(this);
	}

	public void removeRelationalProductImage(ProductImage productImage) {
		productImages.remove(productImage);
		productImage.setProduct(this);
	}
}
