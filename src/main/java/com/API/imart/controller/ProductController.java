package com.API.imart.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.API.imart.entities.Products;
import com.API.imart.entities.Seller;
import com.API.imart.repository.ProductRepository;
import com.API.imart.repository.SellerRepository;
import com.API.imart.services.AuditLogService;

import com.API.imart.services.ProductService;
import com.API.imart.services.SellerService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productservice;
	@Autowired
	private SellerService SService;
	@Autowired
	private SellerRepository Srepo;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private ProductRepository Prepo;
	
	

	// product fetch by seller id
	@GetMapping("/productsOfSeller")
	public ResponseEntity<?> getProductsBySeller(HttpSession session) {
		// Retrieve sellerId from session
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
		}

		// Check if the seller exists
		Seller seller = SService.getSellerById(sellerId);
		if (seller == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller not found with id: " + sellerId);
		}

		// Fetch products for the seller
		List<Products> products = productservice.getProductsBySeller(sellerId);

		// ✅ Add audit log
		auditLogService.saveAuditLog("Fetched products", "Seller ID: " + sellerId + " retrieved their product list."
				);

		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT)
					.body("No products found for seller with id: " + sellerId);
		} else {
			return ResponseEntity.ok(Map.of("data", products));
		}
	}

	// Add Product
	@PostMapping("product/add")
	public ResponseEntity<?> addProducts(@RequestBody Products product, HttpSession session) {
		// ✅ Retrieve sellerId from session
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
		}

		// Fetch seller
		Seller seller = Srepo.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));

		// ✅ Pass `Seller` instead of `sellerId`
		Products createdProduct = productservice.addProduct(product, seller);
		// ✅ Add audit log
		auditLogService.saveAuditLog("Product added",
				"Seller ID: " + sellerId + ", Product Name: " + createdProduct.getProdname());
		return ResponseEntity.ok(createdProduct);
	}

	// update Product of seller
	@PatchMapping("/updateProducts/{productid}")
	public ResponseEntity<?> updateProduct(@PathVariable int productid, @RequestBody Products productDetails,
			HttpSession session) {
		// Retrieve sellerId from session
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
		}

		// Find the product
		Products existingProduct = productservice.getProductById(productid);
		if (existingProduct == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with id: " + productid);
		}

		// Ensure the product belongs to the logged-in seller
		if (existingProduct.getSeller().getId() != sellerId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this product.");
		}

		// ✅ Update only non-null fields
		if (productDetails.getProdname() != null) {
			existingProduct.setProdname(productDetails.getProdname());
		}
		if (productDetails.getPrice() != null) {
			existingProduct.setPrice(productDetails.getPrice());
		}
		if (productDetails.getDescription() != null) {
			existingProduct.setDescription(productDetails.getDescription());
		}
		if (productDetails.getProductimage() != null) {
			existingProduct.setProductimage(productDetails.getProductimage());
		}
		if (productDetails.getWeight() != 0) { // Ensuring weight update is intentional
			existingProduct.setWeight(productDetails.getWeight());
		}
		if (productDetails.getBrand() != null) {
			existingProduct.setBrand(productDetails.getBrand());
		}
		if (productDetails.getSku() != null) {
			existingProduct.setSku(productDetails.getSku());
		}
		if (productDetails.getDiscount() != null) {
			existingProduct.setDiscount(productDetails.getDiscount());
		}
		if (productDetails.getDiscountStart() != null) {
			existingProduct.setDiscountStart(productDetails.getDiscountStart());
		}
		if (productDetails.getDiscountEnd() != null) {
			existingProduct.setDiscountEnd(productDetails.getDiscountEnd());
		}
		if (productDetails.getIs_available() != null) {
			existingProduct.setIs_available(productDetails.getIs_available());
		}
		if (productDetails.getMaterial() != null) {
			existingProduct.setMaterial(productDetails.getMaterial());
		}
		if (productDetails.getQuantity_per_pack() != null) {
			existingProduct.setQuantity_per_pack(productDetails.getQuantity_per_pack());
		}
		if (productDetails.getPackagingtype() != null) {
			existingProduct.setPackagingtype(productDetails.getPackagingtype());
		}
		if (productDetails.getPackagingsize() != null) {
			existingProduct.setPackagingsize(productDetails.getPackagingsize());
		}
		if (productDetails.getPack_types_available() != null) {
			existingProduct.setPack_types_available(productDetails.getPack_types_available());
		}
		if (productDetails.getTexture() != null) {
			existingProduct.setTexture(productDetails.getTexture());
		}
		if (productDetails.getDelivery_time() != null) {
			existingProduct.setDelivery_time(productDetails.getDelivery_time());
		}
		if (productDetails.getService_code() != null) {
			existingProduct.setService_code(productDetails.getService_code());
		}
		if (productDetails.getIs_verified() != null) {
			existingProduct.setIs_verified(productDetails.getIs_verified());
		}
		if (productDetails.getRating() != null) {
			existingProduct.setRating(productDetails.getRating());
		}
		if (productDetails.getShelflife() != null) {
			existingProduct.setShelflife(productDetails.getShelflife());
		}
		if (productDetails.getMinimum_order_qty() != null) {
			existingProduct.setMinimum_order_qty(productDetails.getMinimum_order_qty());
		}
	

		// Update timestamp 
		existingProduct.setUpdatedAt(LocalDateTime.now());

		// Save the updated product
		Products updatedProduct = productservice.save(existingProduct);
		auditLogService.saveAuditLog("Product updated", "Seller ID: " +  sellerId);
		return ResponseEntity.ok(updatedProduct);
	}

	// delete Product of seller
	@DeleteMapping("/deleteProducts/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable int productId, HttpSession session) {
		// Retrieve sellerId from session
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
		}

		// Find the product
		Products existingProduct = productservice.getProductById(productId);
		if (existingProduct == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with id: " + productId);
		}

		// Check if the product belongs to the logged-in seller
		if (existingProduct.getSeller().getId() != sellerId) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this product.");
		}

		// Delete the product
		try {
			productservice.deleteProduct(productId);

			// ✅ Add audit log
			auditLogService.saveAuditLog("Product deleted", "Seller ID: " + sellerId + ", Product ID: " + productId
				);

			return ResponseEntity.ok("✅ Product deleted successfully!");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting product: " + e.getMessage());
		}
	}

	// Code for foreign key

	public ProductController(ProductService productService) {
		this.productservice = productService;
	}

	// add Product in there specific category
	@PostMapping("/addProductInCategory/{categoryId}")
	public ResponseEntity<Products> addProducts(@PathVariable int categoryId, @RequestBody Products products,
			HttpSession session) {
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		Products savedProduct = productservice.addProductToCategory(categoryId, products);

		// ✅ Add audit log
		auditLogService.saveAuditLog("Product added to category", "Seller ID: " + sellerId + ", Product Name: "
				+ savedProduct.getProdname() + ", Category ID: " + categoryId);

		return ResponseEntity.ok(savedProduct);
	}
	
	
	private static final String BASE_UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

	
	
	@PostMapping("/productsImage/upload")
	public ResponseEntity<String> uploadImage(HttpSession session, @RequestBody MultipartFile file) {
	    // Check if user is logged in
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
    	if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
    	Seller seller = Srepo.findById(sellerId).orElse(null);

    

    	    if (file.isEmpty()) {
    	        return ResponseEntity.badRequest().body("File is empty.");
    	    }

    	    try {
    	        // Define user-specific upload folder
    	        String userFolderPath = BASE_UPLOAD_DIR + seller.getUsername() + "/";
    	        File userDir = new File(userFolderPath);

    	        // ✅ Create directory if it does not exist
    	        if (!userDir.exists()) {
    	            if (!userDir.mkdirs()) {
    	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create directory.");
    	            }
    	        }

    	        // Generate unique filename
    	        String originalFilename = file.getOriginalFilename();
    	        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    	        String newFileName = UUID.randomUUID().toString() + extension;

    	        // ✅ Save file in the correct location
    	        File destinationFile = new File(userFolderPath, newFileName);
    	        file.transferTo(destinationFile);

    	        // ✅ Return file path
    	        String imageUrl = "/uploads/" + seller.getUsername() + "/" + newFileName;
    	        return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);

    	    } catch (IOException e) {
    	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
    	    }
    	}

	
	 //All Products
	  
	  @GetMapping public List<Products> getProducts() { return
	  productservice.getAllProducts(); }

	   
	    
}















 

/*
 * // Add Product
 * 
 * @PostMapping("/products/add") public Products addProduct(@RequestBody
 * Products products) { return productservice.addProducts(products); }
 * 
 * // Update product
 * 
 * @PutMapping("/products/update") public Products updateProducts(@RequestBody
 * Products products) { return productservice.updateProducts(products); }
 * 
 * // Delete Product
 * 
 * @DeleteMapping("/products/delete/{id}") public void
 * deleteProducts(@PathVariable int id) { productservice.deleteProducts(id); }
 */

/*
 * public ProductController(ProductRepository productRepository, SellerService
 * sellerService) { this.PRepo = productRepository; this.SService =
 * sellerService; }
 */
// Fetch products of logged-in seller
//	@GetMapping("/sellerproductList")
//	public List<Products> getSellerProducts(Authentication authentication) {
//		Seller seller = SService.getSellerByUsername(authentication.getName()).orElse(null);
//		return PRepo.findBySeller(seller);
//	}

/*
 * // Add a new product (only seller can add)
 * 
 * @PostMapping("/addProduct") public ResponseEntity<Products>
 * addProduct(@RequestBody Products products, Authentication authentication) {
 * Seller seller =
 * SService.getSellerByUsername(authentication.getName()).orElse(null);
 * products.setSeller(seller); return ResponseEntity.ok(PRepo.save(products)); }
 */

// Update product (only if it belongs to the seller)
//	@PutMapping("updateProduct/{productId}")
//	public ResponseEntity<Products> updateProduct(@PathVariable int productId, @RequestBody Products productDetails,
//			Authentication authentication) {
//		Seller seller = SService.getSellerByUsername(authentication.getName()).orElse(null);
//		Products product = PRepo.findById(productId).orElse(null);
//
//		if (product == null || !product.getSeller().equals(seller)) {
//			return ResponseEntity.status(403).build(); // Forbidden
//		}
//
//		product.setProdname(productDetails.getProdname());
//		product.setDescription(productDetails.getDescription());
//		product.setPrice(productDetails.getPrice());
//		product.setWeight(productDetails.getWeight());
//		product.setBrand(productDetails.getBrand());
//		product.setProductimage(productDetails.getProductimage());
//		product.setDiscount(productDetails.getDiscount());
//        product.setIs_available(productDetails.getIs_available());
//		product.setSku(productDetails.getSku());
//		product.setDiscountStart(productDetails.getDiscountStart());
//
//		return ResponseEntity.ok(PRepo.save(product));
//	}

//	// Delete product (only if it belongs to the seller)
//	@DeleteMapping("deleteProduct/{productId}")
//	public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
//		Seller seller = SService.getSellerByUsername.orElse(null;
//		Products product = PRepo.findById(productId).orElse(null);
//
//		if (product == null || !product.getSeller().equals(seller)) {
//			return ResponseEntity.status(403).build(); // Forbidden
//		}
//
//		PRepo.delete(product);
//		return ResponseEntity.noContent().build();
//	}
