package com.alejandro.ws.service;

import java.util.Optional;
import java.util.Set;

import com.alejandro.ws.entity.Product;

public interface AbstractService {

	public Set<Product> findAll();
	
	public Optional<Product> findByName(String name); 
}
