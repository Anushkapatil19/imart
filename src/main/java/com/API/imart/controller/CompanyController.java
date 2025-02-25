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

import com.API.imart.entities.CompanyDetails;
import com.API.imart.entities.Seller;
import com.API.imart.repository.CompanyRepository;
import com.API.imart.repository.SellerRepository;
import com.API.imart.services.AuditLogService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/seller")
public class CompanyController {

	@Autowired
	private CompanyRepository CRepo;
	
	@Autowired
	private SellerRepository SRepo;
	
	@Autowired
	private AuditLogService auditLogService;

	// Fetch Company Details
	@GetMapping("/company")
	public ResponseEntity<?> getCompanyDetails(HttpSession session) {
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    CompanyDetails companyDetails = CRepo.findBySellerId(sellerId);

	    if (companyDetails == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No company details found for seller ID: " + sellerId);
	    }

	    // ✅ Log the activity
	    auditLogService.saveAuditLog("Fetched company details", "Seller ID: " + sellerId);

	    return ResponseEntity.ok(companyDetails);
	}



	/*
	 * //@GetMapping("{id}") //public Seller
	 * findSellerWithCompanyDetails(@PathVariable int id) { //
	 * SService.findSellerWithCompanyDetails(id); }
	 */
	// Create Company Details

	@PostMapping("/addCompanyDetails")
	public ResponseEntity<?> createCompanyDetails(@RequestBody CompanyDetails companyDetails, HttpSession session) {
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    Seller seller = SRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found!"));
	    companyDetails.setSeller(seller);

	    CompanyDetails savedCompany = CRepo.save(companyDetails);

	    // ✅ Log the activity
	    auditLogService.saveAuditLog("Added company details", "Seller ID: " +  sellerId);

	    return ResponseEntity.ok(savedCompany);
	}

	
	
	@PatchMapping("/updateCompanyDetails")
	public ResponseEntity<?> updateCompanyDetails(@RequestBody CompanyDetails updatedDetails, HttpSession session) {
	    // ✅ Get sellerId from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    // ✅ Fetch existing company details for this seller
	    CompanyDetails existingCompany = CRepo.findBySellerId(sellerId);
	    if (existingCompany == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No company details found for seller ID: " + sellerId);
	    }

	    // ✅ Update only non-null fields
	    if (updatedDetails.getCompanyName() != null) {
	        existingCompany.setCompanyName(updatedDetails.getCompanyName());
	    }
	    if (updatedDetails.getWebsite() != null) {
	        existingCompany.setWebsite(updatedDetails.getWebsite());
	    }
	    if (updatedDetails.getFacebookHandle() != null) {
	        existingCompany.setFacebookHandle(updatedDetails.getFacebookHandle());
	    }
	    if (updatedDetails.getGstin() != null) {
	        existingCompany.setGstin(updatedDetails.getGstin());
	    }
	    if (updatedDetails.getPan() != null) {
	        existingCompany.setPan(updatedDetails.getPan());
	    }
	    if (updatedDetails.getInstagramHandle() != null) {
	        existingCompany.setInstagramHandle(updatedDetails.getInstagramHandle());
	    }

	    // ✅ Save the updated company details
	    CompanyDetails savedCompany = CRepo.save(existingCompany);
	    auditLogService.saveAuditLog("CompanyDetails updated", "Seller ID: " + sellerId);
	    return ResponseEntity.ok(savedCompany);
	}



}
