package com.API.imart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.imart.entities.ChangePasswordDTO;
import com.API.imart.services.SellerService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class ChangePasswordController {

  @Autowired
  private SellerService SService;
    
  @PostMapping("/change-password")
  public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO request, HttpSession session) {
    	return SService.changePassword(request, session); // ✅ No need to wrap in ResponseEntity.ok(response)
    }
    
    
    
    
    
    
    /*
	 * @PostMapping("/change-password") public String changePassword(Principal
	 * principal, @RequestParam String oldPassword, @RequestParam String
	 * newPassword) { if (principal == null) { return "User not authenticated!"; }
	 * 
	 * String username = principal.getName(); Optional<Seller> sellerOptional =
	 * SRepo.findByUsername(username);
	 * 
	 * if (sellerOptional.isPresent()) { Seller seller = sellerOptional.get();
	 * 
	 * // Check if old password matches if (!passwordEncoder.matches(oldPassword,
	 * seller.getPassword())) { return "Old password is incorrect!"; }
	 * 
	 * // Encrypt new password and update
	 * seller.setPassword(passwordEncoder.encode(newPassword)); SRepo.save(seller);
	 * 
	 * return "Password updated successfully!"; }
	 * 
	 * return "User not found"; }
	 */
    

}



