package vn.devpro.javaweb35.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.devpro.javaweb35.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
	public Boolean existsByName(String name);

	Optional<Category> findById(Integer id);

	public List<Category> findByStatusTrue();
}
