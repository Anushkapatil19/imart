package com.API.imart.services;

import java.time.LocalDateTime;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.API.imart.entities.Category;
import com.API.imart.entities.Products;
import com.API.imart.entities.Seller;
import com.API.imart.repository.CategoryRepository;
import com.API.imart.repository.ProductRepository;
import com.API.imart.repository.SellerRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository PRepo;
	@Autowired
	private CategoryRepository CateRepo;
	@Autowired
	private SellerRepository sellerrepo;
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Products> getAllProducts() {
		return PRepo.findAll();
	}

	// code for add product
	public Products addProducts(Products products) {
		products.setCreatedAt(LocalDateTime.now());
		return PRepo.save(products);
	}

	// code for update product

	// Method to get a product by ID
	public Products getProductById(int productId) {
		return PRepo.findById(productId).orElse(null);
	}

	// ✅ Add Category (Now accepts Seller instead of sellerId)
		public Products addProduct(Products product, Seller seller) {
			product.setSeller(seller); // Associate category with the seller
			product.setCreatedAt(LocalDateTime.now());
			return PRepo.save(product);
		}
		
	// Your existing update method
	public Products updateProducts(int productId, Products productDetails) {
		Products existingProduct = getProductById(productId);
		
		if (existingProduct == null) {
			throw new RuntimeException("Product not found with ID: " + productId);
		}

		// Update fields
		existingProduct.setDescription(productDetails.getDescription());
		existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setUpdatedAt(LocalDateTime.now());
		return PRepo.save(existingProduct);
	}

	// ✅ Add this save method
	public Products save(Products product) {
		return PRepo.save(product);
	}

	// code for delete product

	// ✅ Method to delete a product
	public void deleteProduct(int productId) {
		Products existingProduct = getProductById(productId);

		if (existingProduct == null) {
			throw new RuntimeException("Product not found with ID: " + productId);
		}

		PRepo.deleteById(productId);
	}

	// code for add product in their specific category
	public Products addProductToCategory(int categoryId, Products products) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found"));

		products.setCategory(category);
		return PRepo.save(products);
	}

	// Foreign key code

	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.PRepo = productRepository;
		this.CateRepo = categoryRepository;
	}

	@Transactional
	public Products addProductToCategory(int categoryId, Products products, int uid) {
		Seller seller = sellerrepo.findById(uid).orElse(null);
		Category category = CateRepo.findById(categoryId)
				.orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
		products.setSeller(seller);
		products.setCategory(category);
		return PRepo.save(products);
	}

	// Code to fetch products of specific category id
	public List<Products> getProductsByCategoryId(int categoryId) {
		return PRepo.findByCategoryId(categoryId);
	}

	// Code to fetch products of specific seller id
	public List<Products> getProductsBySeller(int sellerid) {
		return PRepo.findBySellerId(sellerid);
	}
}
