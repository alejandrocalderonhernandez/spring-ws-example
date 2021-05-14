package com.alejandro.ws.repocitory;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.alejandro.ws.entity.Product;

public interface ProductRepocitory extends CrudRepository<Product, Long> {

	@Query("select p from Product p where p.name = name")
	public Optional<Product> findByProductName(@Param("name") String name);
	
	@Query("select p from Product p")
	public Set<Product> findAll();
}
