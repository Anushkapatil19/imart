package com.API.imart.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.API.imart.entities.ChangePasswordDTO;
import com.API.imart.entities.Seller;
import com.API.imart.repository.SellerRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class SellerService {

	@Autowired
	private SellerRepository SRepo;

	// User Registration
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private AuditLogService auditLogService;
	
	public SellerService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	// User Registration with Encoded Password
	@Transactional
	public Seller register(Seller seller) {
		// Encode the password before saving the buyer
		String encodedPassword = passwordEncoder.encode(seller.getPassword());
		seller.setPassword(encodedPassword);

		return SRepo.save(seller);
	}

	@Autowired
	private SellerRepository Srepo;

	public Seller findSellerWithBankDetails(int id) {
		return Srepo.findSellerWithBankDetails(id);
	}

	// Seller By there Id
	public Seller getSellerById(int id) {
		return Srepo.findById(id).orElseThrow(() -> new RuntimeException("Seller not found"));
	}

	// Update Seller Info
	public Seller updateSeller(int sellerid, Seller seller) {
		Seller existingSeller = getSellerById(sellerid);
		existingSeller.setName(seller.getName());
		existingSeller.setEmail(seller.getEmail());
		existingSeller.setPhone(seller.getPhone());
		existingSeller.setDesignation(seller.getDesignation());
		existingSeller.setAltEmail(seller.getAltEmail());
		existingSeller.setAltPhone(seller.getAltPhone());

		return Srepo.save(existingSeller);
	}

	// Delete Seller
	public ResponseEntity<?> deleteSeller(int sellerid) {
		Srepo.deleteById(sellerid);
		return ResponseEntity.ok("Deleted Sucessfully!");
	}

	public Optional<Seller> getSellerByUsername(String username) {
		return SRepo.findByUsername(username);
	}

	@Transactional
	public ResponseEntity<?> changePassword(ChangePasswordDTO request, HttpSession session) {

	    try {
	        // ✅ Retrieve sellerId from session
	        Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	        if (sellerId == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	        }

	        // ✅ Fetch the logged-in seller from DB
	        Seller seller = SRepo.findById(sellerId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // ✅ Check if old password matches
	        if (!passwordEncoder.matches(request.getOldPassword(), seller.getPassword())) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password");
	        }

	        // ✅ Update password
	        seller.setPassword(passwordEncoder.encode(request.getNewPassword()));
	        SRepo.save(seller);
	     // ✅ Audit Logging (without storing passwords)
	        auditLogService.saveAuditLog("Password Changed", "Seller ID: " + sellerId + " changed password.");
	        return ResponseEntity.ok("Password changed successfully");

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred: " + e.getMessage());
	    }
	}


//public Seller findSellerWithAddressDetails(int id)
	{
		// return Srepo.findSellerWithAddressDetails(id);
	}

	// public Seller findSellerWithCompanyDetails(int id)

	// return Srepo.findSellerWithCompanyDetails(id);
}