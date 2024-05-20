package com.adller.thysb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adller.thysb.models.Product;
import com.adller.thysb.models.ProductDTO;
import com.adller.thysb.services.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	private ProductsRepository repo;
	
	@GetMapping({"", "/"})
	public String showProductList(Model model) {
		List<Product> products = repo.findAll();
		model.addAttribute("products", products);
		return "products/index";
	}
	
	@GetMapping({"/create"})
	public String showCreatePage(Model model) {
		ProductDTO productDTO = new ProductDTO();
		model.addAttribute("productDTO", productDTO);
		return "products/CreateProduct";
	}
	
	@PostMapping({"/create"})
	public String createProduct(
			@Valid @ModelAttribute ProductDTO productDTO,
			BindingResult result
			) {
		
		if(result.hasErrors()) {
			return "products/CreateProduct";
		};
		
		
		
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setCreatedAt(product.getCreatedAt());
		
		repo.save(product);
		
		return "redirect:/products";
	}
	
	@GetMapping({"/edit"})
	public String showEditPage(
			Model model,
			@RequestParam int id
		) {
		
		try {
			Product product = repo.findById(id).get();
			model.addAttribute("product", product);
			
			ProductDTO productDTO = new ProductDTO();
			productDTO.setName(product.getName());
			productDTO.setPrice(product.getPrice());
			
			model.addAttribute("productDTO", productDTO);
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/products";
		}
		
		return "products/EditProduct";
	}
	
	@PostMapping({"/edit"})
	public String updateProduct(
			Model model,
			@RequestParam int id,
			@Valid @ModelAttribute ProductDTO productDTO,
			BindingResult result
			) {
		
		try {
			Product product = repo.findById(id).get();
			model.addAttribute("product", product);
			
			if(result.hasErrors()) {
				return "products/EditProduct";
			};
			
			
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());
			
			repo.save(product);
		} 
		catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/products";
	}
	
	@GetMapping("/delete")
	public String deleteProduct(
			@RequestParam int id
			) {
		
		try {
			Product product = repo.findById(id).get();
			
			repo.delete(product);
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/products";
	}
}
