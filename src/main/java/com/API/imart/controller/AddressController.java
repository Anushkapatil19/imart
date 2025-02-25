package com.API.imart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.API.imart.entities.AddressDetails;
import com.API.imart.entities.Seller;
import com.API.imart.repository.AddressRepository;
import com.API.imart.repository.SellerRepository;
import com.API.imart.services.AddressService;
import com.API.imart.services.AuditLogService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/seller")
public class AddressController {

	@Autowired
	private AddressRepository ARepo;

	@Autowired
	private SellerRepository SRepo;
	
	@Autowired
	private AddressService Aservice;
	
	  @Autowired
	    private AuditLogService auditLogService;
	
	// Fetch Address
	@GetMapping("/address")
	public ResponseEntity<?> getAddress(HttpSession session) {
	    // ✅ Retrieve sellerId from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    // ✅ Fetch the address for the logged-in seller
	    AddressDetails address = ARepo.findBySellerId(sellerId);

	    if (address == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found for seller ID: " + sellerId);
	    }

	    return ResponseEntity.ok(address);
	}


	// Create Address
	@PostMapping("/addAddress")
	public ResponseEntity<?> createAddress(@RequestBody AddressDetails address, HttpSession session) {
	    // ✅ Retrieve sellerId from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    // ✅ Fetch the seller from DB
	    Seller seller = SRepo.findById(sellerId)
	            .orElseThrow(() -> new RuntimeException("Seller not found"));

	    // ✅ Link the address to the seller
	    address.setSeller(seller);

	    // ✅ Save the address
	    AddressDetails savedAddress = ARepo.save(address);
	    // ✅ Audit log entry
        auditLogService.saveAuditLog("Address Added", "Seller ID: " + sellerId + ", Address: " + address.toString());
	    return ResponseEntity.ok(savedAddress);
	}

	
	//Edit Address
	@PatchMapping("/updateAddress")
	public ResponseEntity<?> editAddress(@RequestBody AddressDetails addressDetails, HttpSession session) {
	    // ✅ Get sellerId from session
	    Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	    if (sellerId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No seller ID found in session. Please log in.");
	    }

	    // ✅ Fetch the existing address for the seller
	    AddressDetails existingAddress = ARepo.findBySellerId(sellerId);

	    if (existingAddress == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No address found for seller ID: " + sellerId);
	    }

	    // ✅ Update only non-null fields
	    if (addressDetails.getArea() != null) {
	        existingAddress.setArea(addressDetails.getArea());
	    }
	    if (addressDetails.getCity() != null) {
	        existingAddress.setCity(addressDetails.getCity());
	    }
	    if (addressDetails.getHouseNo() != null) {
	        existingAddress.setHouseNo(addressDetails.getHouseNo());
	    }
	    if (addressDetails.getLandmark() != null) {
	        existingAddress.setLandmark(addressDetails.getLandmark());
	    }
	    if (addressDetails.getLocality() != null) {
	        existingAddress.setLocality(addressDetails.getLocality());
	    }
	    if (addressDetails.getPinCode() != null) {
	        existingAddress.setPinCode(addressDetails.getPinCode());
	    }
	    if (addressDetails.getCountry() != null) {
	        existingAddress.setCountry(addressDetails.getCountry());
	    }
	    if (addressDetails.getState() != null) {
	        existingAddress.setState(addressDetails.getState());
	    }

	    // ✅ Save updated address
	    AddressDetails updatedAddress = ARepo.save(existingAddress);
	 // ✅ Audit log entry
        auditLogService.saveAuditLog("Address Updated", "Seller ID: " + sellerId + ", Updated Address: " + updatedAddress.toString());

	    return ResponseEntity.ok(updatedAddress);
	}


	//Delete Address
	@DeleteMapping("/deleteAddress")
    public ResponseEntity<?> deleteAddress(HttpSession session) {
        // ✅ Get sellerId from session
        Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

        if (sellerId == null) {
            return ResponseEntity.status(401).body("No seller ID found in session. Please log in.");
        }

        try {
            String response = Aservice.deleteAddress(sellerId);
         // ✅ Audit log entry
            auditLogService.saveAuditLog("Address Deleted", "Seller ID: " + sellerId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
   }

	
	// @GetMapping("/{id}")
//		public Seller findSellerWithAddressDetails(@PathVariable int id)
	{
		// return SService.findSellerWithAddressDetails(id);
	}
}
