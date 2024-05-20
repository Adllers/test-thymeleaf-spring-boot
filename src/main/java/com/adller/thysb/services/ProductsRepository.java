package com.adller.thysb.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adller.thysb.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer>{
	
}
