package vn.devpro.javaweb35.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.devpro.javaweb35.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
	Optional<Product> findById(Integer id);

	public Boolean existsByName(String name);

	Page<Product> findByStatusTrue(Pageable pageable);

//	Page<Product> findByNameContainingIgnoreCase(String keyword,Pageable pageable);

	Boolean existsByNameAndIdNot(String name, Integer id);

}
