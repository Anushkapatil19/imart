package com.API.imart.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.API.imart.entities.Category;
import com.API.imart.entities.Seller;
import com.API.imart.repository.CategoryRepository;


@Service
public class CategoryService {

	/*
	 * @Autowired CategoryRepository categoryrepo; public List<Category>
	 * getAllCategory() { return categoryrepo.findAll(); }
	 */


	@Autowired
	private CategoryRepository categoryRepository;

	

	// ✅ Add Category (Now accepts Seller instead of sellerId)
	public Category addCategory(Category category, Seller seller) {
		category.setSeller(seller); // Associate category with the seller
		category.setCreatedAt(LocalDateTime.now());
		return categoryRepository.save(category);
	}

	// Get All Categories
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	// Get Category by ID
	public Optional<Category> getCategoryById(int id) {
		return categoryRepository.findById(id);
	}

	// code for update category
	
	   
	    public Category updateCategory(int categoryId, Category categoryDetails, Integer sellerId) {
	        // ✅ Fetch existing category (Fix Optional issue)
	        Category existingCategory = categoryRepository.findById(categoryId)
	                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

	        // ✅ Ensure the category belongs to the logged-in seller
	        if (existingCategory.getSeller().getId() != sellerId) {
	            throw new RuntimeException("You are not authorized to update this category.");
	        }


	        // ✅ Update only non-null fields
	        if (categoryDetails.getCategoryName() != null) {
	            existingCategory.setCategoryName(categoryDetails.getCategoryName());
	        }
	        if (categoryDetails.getCategory_image_url() != null) {
	            existingCategory.setCategory_image_url(categoryDetails.getCategory_image_url());
	        }
	        if (categoryDetails.getDescription() != null) {
	            existingCategory.setDescription(categoryDetails.getDescription());
	        }
	        if (categoryDetails.getExcel_sheet() != null) {
	            existingCategory.setExcel_sheet(categoryDetails.getExcel_sheet());
	        }
	        if (categoryDetails.getHighlighted_points() != null) {
	            existingCategory.setHighlighted_points(categoryDetails.getHighlighted_points());
	        }
	        if (categoryDetails.getInfo_file() != null) {
	            existingCategory.setInfo_file(categoryDetails.getInfo_file());
	        }
	        if (categoryDetails.getOrigin() != null) {
	            existingCategory.setOrigin(categoryDetails.getOrigin());
	        }
	        if (categoryDetails.getSpecification() != null) {
	            existingCategory.setSpecification(categoryDetails.getSpecification());
	        }

	        existingCategory.setUpdatedAt(LocalDateTime.now()); // ✅ Update timestamp

	        // ✅ Save updated category
	        return categoryRepository.save(existingCategory);
	    }
	


	// code for delete category
	public ResponseEntity<?> deleteCategory(int categoryid,int sellerId) {
		Category category = categoryRepository.findById(categoryid)
				.orElseThrow(() -> new RuntimeException("Category not found"));
		category.setDeletedAt(LocalDateTime.now());
		
		category.setDeletedBy(sellerId);
		if (categoryRepository.existsById(categoryid)) {
			categoryRepository.deleteById(categoryid);
			
			return ResponseEntity.ok("Category Deleted!!");
		} else {
			throw new RuntimeException("Category not found with ID: " + categoryid);
		}
	}
}
