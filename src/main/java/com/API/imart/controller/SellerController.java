package com.API.imart.controller;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.API.imart.entities.Seller;
import com.API.imart.repository.SellerRepository;
import com.API.imart.services.AuditLogService;
import com.API.imart.services.SellerService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

	@Autowired
	private SellerRepository SRepo;

	@Autowired
	private SellerService SService;

	@Autowired
	private AuditLogService auditLogService;



	// Fetch Seller Info
	@GetMapping("/viewProfile")
	public ResponseEntity<?> getProfile(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
		}

		Seller seller = SService.getSellerById(userId); // ❌ Returns Seller, not Optional<Seller>

		if (seller == null) {
			return ResponseEntity.notFound().build();
		}
		auditLogService.saveAuditLog("View Profile", "Seller ID: " + userId + " viewed their profile.");

		return ResponseEntity.ok(Map.of("data", seller));
		// ✅ Corrected version
	}

	// Update Seller Info
	@PatchMapping("/profileUpdate")
	public ResponseEntity<?> updateSeller(@RequestBody Seller sellerDetails, HttpSession session) {
	    // Retrieve sellerId from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body("No seller ID found in session. Please log in.");
	    }

	    // Find seller by ID
	    Seller existingSeller = SRepo.findById(sellerId).orElse(null);
	    if (existingSeller == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Seller not found with id: " + sellerId);
	    }

	    // Update only the non-null fields
	    if (sellerDetails.getName() != null) {
	        existingSeller.setName(sellerDetails.getName());
	    }
	    if (sellerDetails.getEmail() != null) {
	        existingSeller.setEmail(sellerDetails.getEmail());
	    }
	    if (sellerDetails.getPassword() != null) {
	        existingSeller.setPassword(sellerDetails.getPassword()); // Consider encrypting this
	    }
	    if (sellerDetails.getPhone() != null) {
	        existingSeller.setPhone(sellerDetails.getPhone());
	    }
	    if (sellerDetails.getDesignation() != null) {
	        existingSeller.setDesignation(sellerDetails.getDesignation());
	    }
	    if (sellerDetails.getProfilePicture() != null) {
	        existingSeller.setProfilePicture(sellerDetails.getProfilePicture());
	    }
	    if (sellerDetails.getAltEmail() != null) {
	        existingSeller.setAltEmail(sellerDetails.getAltEmail());
	    }
	    if (sellerDetails.getAltPhone() != null) {
	        existingSeller.setAltPhone(sellerDetails.getAltPhone());
	    }
	    if (sellerDetails.getUsername() != null) {
	        existingSeller.setUsername(sellerDetails.getUsername());
	    }
	    
	    
	    // Update timestamp
	    existingSeller.setUpdatedat(LocalDateTime.now());

	    // Save updated seller details
	    Seller updatedSeller = SRepo.save(existingSeller);
	 // Audit Log
        auditLogService.saveAuditLog("Seller Updated", "Seller ID: " + sellerId + " updated their profile.");
	    return ResponseEntity.ok(Map.of("message", "Profile updated successfully", "data", updatedSeller));
	}

	// Delete Seller
	@DeleteMapping("/profileDelete")
	public ResponseEntity<?> deleteSeller(HttpSession session) {
		// Retrieve sellerId from session
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
		}

		// Find seller by ID
		Seller seller = SRepo.findById(sellerId).orElse(null);
		if (seller == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seller not found with id: " + sellerId);
		}
		
		// Log before session invalidation
        auditLogService.saveAuditLog("Seller Deleted", "Seller ID: " + sellerId + " deleted their profile.");

		// Delete seller
		ResponseEntity<?> msg = SService.deleteSeller(sellerId);

		// Invalidate session after deletion
		session.invalidate();

		return msg;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


//	@PutMapping("/profileUpdate/{sellerid}")
//
//	public ResponseEntity<?> updateSeller(@PathVariable int sellerid, @RequestBody Seller sellerDetails) {
//		Seller seller = SRepo.findById(sellerid).orElse(null);
//		if (seller == null) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seller not found with id: " + sellerid);
//		} else {
//			Seller updatedSeller = SService.updateSeller(sellerid, sellerDetails);
//			return ResponseEntity.ok(updatedSeller);
//		}
//	}

//	@DeleteMapping("/profileDelete/{sellerid}")
//	public ResponseEntity<?> deleteSeller(@PathVariable int sellerid) {
//		Seller seller = SRepo.findById(sellerid).orElse(null);
//		if (seller == null) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seller not found with id: " + sellerid);
//		} else {
//			ResponseEntity<?> msg = SService.deleteSeller(sellerid);
//			return msg;
//
//		}
//	}

}

//	// All Sellers
//	@GetMapping
//	public List<Seller> getAllSeller() {
//		return SRepo.findAll();
//	}

// Fetch Seller Info
//	@GetMapping("/profile")
//	public Seller getSellerInfo(Authentication authentication) {
//		Seller seller = SService.getSellerByUsername(authentication.getName()).orElse(null);
//		return SRepo.findById(seller.getId()).orElse(null);
//	}

/*
 * // Create Seller Info
 * 
 * @PostMapping("/createsellerinfo") public Seller createSellerInfo(@RequestBody
 * Seller sellerInfo) { return SRepo.save(sellerInfo); }
 */
