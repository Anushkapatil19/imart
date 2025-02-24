package com.API.imart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.API.imart.entities.BankDetails;
import com.API.imart.entities.Seller;
import com.API.imart.repository.BankRepository;
import com.API.imart.repository.SellerRepository;
import com.API.imart.services.AuditLogService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/seller")
public class BankController {
	@Autowired
	private BankRepository BRepo;
	@Autowired
	private SellerRepository SRepo;
	@Autowired
    private AuditLogService auditLogService; 


	// Fetch Bank Details
	@GetMapping("/bankDetails")
	public ResponseEntity<?> getBankDetails(HttpSession session) {
		// ✅ Fetch seller ID from session
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
		}

		// ✅ Fetch bank details linked to the seller
		BankDetails bankDetails = BRepo.findBySellerId(sellerId);

		if (bankDetails == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bank details found for seller ID: " + sellerId);
		}

		return ResponseEntity.ok(bankDetails);
	}

//add BankDetails
	@PostMapping("/addBankDetails")
	public ResponseEntity<?> createBankDetails(@RequestBody BankDetails bankDetails, HttpSession session) {
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
		}

		// ✅ Set the seller for the bank details
		Seller seller = SRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found!"));
		bankDetails.setSeller(seller);

		// ✅ Save the bank details
		BankDetails savedBank = BRepo.save(bankDetails);
		// ✅ Audit Log Entry
        auditLogService.saveAuditLog("Bank Details Added", 
            "Seller ID: " + sellerId + ", Account Number: " + bankDetails.getAccountNumber());

		return ResponseEntity.ok(savedBank);
	}

	// updated details
	@PatchMapping("/bankUpdateDetails")
	public ResponseEntity<?> updateBankDetails(@RequestBody BankDetails updatedBank, HttpSession session) {
	    // ✅ Get seller ID from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    // ✅ Fetch existing bank details for the seller
	    BankDetails existingBank = BRepo.findBySellerId(sellerId);

	    if (existingBank == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bank details found for seller ID: " + sellerId);
	    }

	    // ✅ Update only non-null fields
	    if (updatedBank.getAccountNumber() != null) {
	        existingBank.setAccountNumber(updatedBank.getAccountNumber());
	    }
	    if (updatedBank.getAccountType() != null) {
	        existingBank.setAccountType(updatedBank.getAccountType());
	    }
	    if (updatedBank.getBankName() != null) {
	        existingBank.setBankName(updatedBank.getBankName());
	    }
	    if (updatedBank.getIfscCode() != null) {
	        existingBank.setIfscCode(updatedBank.getIfscCode());
	    }

	    // ✅ Save updated bank details
	    BankDetails savedBank = BRepo.save(existingBank);
	 // ✅ Audit Log Entry
        auditLogService.saveAuditLog("Bank Details Updated", 
            "Seller ID: " + sellerId + ", Updated Account Number: " + updatedBank.getAccountNumber());
	    return ResponseEntity.ok(savedBank);
	}


}
