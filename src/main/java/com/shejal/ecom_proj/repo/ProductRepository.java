package com.shejal.ecom_proj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.shejal.ecom_proj.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	/*
	@Query("Select p from Product p where "
			+ "LOWER(p.name) like LOWER(CONCATE('%',:keyword,'%')) OR "
			+ "LOWER(p.description) LIKE LOWER(CONCATE('%',:keyword,'%')) OR "
			+ "LOWER(p.brand) LIKE LOWER(CONCATE('%',:keyword,'%')) OR "
			+ "LOWER(p.category) LIKE LOWER(CONCATE('%',:keyword,'%'))")*/
	@Query("SELECT p from Product p WHERE "+"LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            +"LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
             +"LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
             +"LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	public List<Product> searchProducts(String keyword);
}
