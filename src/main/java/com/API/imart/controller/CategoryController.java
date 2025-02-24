package com.API.imart.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.API.imart.entities.Category;
import com.API.imart.entities.Seller;
import com.API.imart.repository.CategoryRepository;
import com.API.imart.repository.SellerRepository;
import com.API.imart.services.AuditLogService;
import com.API.imart.services.CategoryService;
import com.API.imart.services.SellerService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	CategoryService categoryservice;
	@Autowired
	CategoryRepository CRepo;

	@Autowired
	private SellerService SService;
	@Autowired
	private SellerRepository Srepo;
	@Autowired
	private AuditLogService auditLogService;

	// for fetch seller categories 
	@GetMapping("/sellercategoryList")
	public ResponseEntity<?> getSellerCategory(HttpSession session) {
	    // Retrieve sellerId from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    // Find the seller
	    Seller seller = SService.getSellerById(sellerId);
	    if (seller == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller not found.");
	    }

	    // Retrieve categories for this seller
	    List<Category> categories = CRepo.findBySeller(seller);
	    
	    if (categories.isEmpty()) {
	    	auditLogService.saveAuditLog("Category Fetch Attempt", 
	                "Seller ID: " + sellerId + " attempted to fetch categories, but none were found.");
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No categories found for this seller.");
	    }

        return ResponseEntity.ok(Map.of("data", categories));
	}


	// Add Category
	@PostMapping("/add")
	public ResponseEntity<?> addCategory(@RequestBody Category category, HttpSession session) {
	    // ✅ Retrieve sellerId from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    // Fetch seller
	    Seller seller = Srepo.findById(sellerId)
	            .orElseThrow(() -> new RuntimeException("Seller not found"));

	    // ✅ Pass `Seller` instead of `sellerId`
	    Category createdCategory = categoryservice.addCategory(category, seller);
	    // ✅ Add audit log
	    auditLogService.saveAuditLog("Category Added", "Seller ID: " + sellerId + ", Category Name: " + category.getCategoryName());

	    return ResponseEntity.ok(createdCategory);
	}


	// update Product of seller
	@PatchMapping("/updateCategory/{categoryid}")
	    public ResponseEntity<?> updateCategory(@PathVariable int categoryid, 
	                                            @RequestBody Category categoryDetails, 
	                                            HttpSession session) {
	        // ✅ Retrieve sellerId from session
	        Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	        if (sellerId == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	        }

	        try {
	            // ✅ Call service to update category
	            Category updatedCategory = categoryservice.updateCategory(categoryid, categoryDetails, sellerId);
	            // ✅ Audit Log
	            auditLogService.saveAuditLog("Category Updated", 
	            		"Seller ID: " + sellerId + ", Category ID: " + categoryid + ", Updated Name: " + categoryDetails.getCategoryName());
	            return ResponseEntity.ok(updatedCategory);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	


	// delete Product of seller
	@DeleteMapping("/deleteCategory/{categoryid}")
	public ResponseEntity<?> deleteCategory(@PathVariable int categoryid, HttpSession session) {
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
		Category categories  =CRepo.findById(sellerId).orElse(null);

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }
		try {
			categoryservice.deleteCategory(categoryid,sellerId);
			// ✅ Audit Log
	        auditLogService.saveAuditLog("Category Deleted", 
	            "Seller ID: " + sellerId + ", Deleted Category ID: " + categoryid);
			return ResponseEntity.ok("✅ Category deleted successfully!");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	/*
	 * // Get All Categories (Accessible to everyone)
	 * 
	 * @GetMapping("/all") public ResponseEntity<List<Category>> getAllCategories()
	 * { return ResponseEntity.ok(categoryService.getAllCategories()); }
	 * 
	 * // Get Category by ID (Accessible to everyone)
	 * 
	 * @GetMapping("/{id}") public ResponseEntity<Category>
	 * getCategoryById(@PathVariable int id) { Optional<Category> category =
	 * categoryService.getCategoryById(id); return category.map(ResponseEntity::ok)
	 * .orElseGet(() -> ResponseEntity.notFound().build()); }
	 */

	/*
	 * // Update Category (Only for logged-in sellers)
	 * 
	 * @PutMapping("/update/{id}") public ResponseEntity<?>
	 * updateCategory(@PathVariable int id, @RequestBody Category category,
	 * Authentication authentication) { Seller seller =
	 * SService.getSellerByUsername(authentication.getName()).orElse(null);
	 * 
	 * 
	 * if (seller == null) { return ResponseEntity.status(HttpStatus.FORBIDDEN).
	 * body("Unauthorized: Only sellers can update categories"); } Category
	 * updatedCategory = categoryService.updateCategory(id, category); return
	 * ResponseEntity.ok(updatedCategory); }
	 * 
	 * // Delete Category (Only for logged-in sellers)
	 * 
	 * @DeleteMapping("/delete/{id}") public ResponseEntity<?>
	 * deleteCategory(@PathVariable int id, Authentication authentication) { Seller
	 * seller = SService.getSellerByUsername(authentication.getName()).orElse(null);
	 * 
	 * 
	 * if (seller == null) { return ResponseEntity.status(HttpStatus.FORBIDDEN).
	 * body("Unauthorized: Only sellers can delete categories"); }
	 * categoryService.deleteCategory(id); return
	 * ResponseEntity.ok("Category deleted successfully"); }
	 */
}