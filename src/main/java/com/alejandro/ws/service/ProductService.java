package com.alejandro.ws.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.ws.entity.Product;
import com.alejandro.ws.repocitory.ProductRepocitory;

@Service
public class ProductService implements AbstractService {

	@Autowired
	private ProductRepocitory repocitory;

	@Override
	public Set<Product> findAll() {
		return this.repocitory.findAll();
	}

	@Override
	public Optional<Product> findByName(String name) {
		return this.repocitory.findByProductName(name);
	}
}
