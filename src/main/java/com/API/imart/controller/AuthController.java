package com.API.imart.controller;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import com.API.imart.entities.Seller;
import com.API.imart.entities.SellerLogin;
import com.API.imart.repository.SellerRepository;
 
import com.API.imart.services.SellerService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SellerRepository SRepo;
	
	// Register API Endpoint ("/register")
	@Autowired
	SellerService sservice;

	@PostMapping("/register")
	public ResponseEntity<?> registerSeller(@RequestBody Seller seller) {

		Seller registeredseller = sservice.register(seller);
		seller.setCreatedat(LocalDateTime.now());
		
		System.out.println("Seller register******" + registeredseller);
//		// ✅ Audit Log Entry
//	    auditLogService.saveAuditLog("Seller Registered", 
//	        "Seller ID: " + registeredseller.getId() + ", Name: " + registeredseller.getName() +", Email: " + registeredseller.getEmail());
		return ResponseEntity.ok(registeredseller);
	}

	@PostMapping("/sellerLogin")
	public ResponseEntity<?> login(@RequestBody SellerLogin request,HttpSession session) {
		String username = request.getUsername();
		String password = request.getPassword();
		Seller seller=SRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        session.setAttribute("LOGGED_IN_USER_ID", seller.getId());
        if(!"Seller".equalsIgnoreCase(seller.getUser_type().toString()))
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Access denied! Seller only."));
		}

		try {
			// Authenticate using Spring Security's AuthenticationManager
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			if (authentication.isAuthenticated()) {
				
				return ResponseEntity.ok(Map.of("message", "Login successful!", "redirectUrl", "/account/dashboard","data",seller));
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed!");
	}
	
	@GetMapping("/session-id")
    public ResponseEntity<?> getLoggedInUserId(HttpSession session) {
	  Integer userId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }
        return ResponseEntity.ok(userId);
    }
	
	
	 @PostMapping("/logout") 
	    public ResponseEntity<String> logout(HttpSession session) {
	        session.invalidate();
	        return ResponseEntity.ok("Logged out successfully");
	    }
	 
	 @PostMapping("/adminLogin")
		public ResponseEntity<?> adminlogin(@RequestBody SellerLogin buyerdto, HttpSession session) {
			String username = buyerdto.getUsername();
			String password = buyerdto.getPassword();
			
			Seller admin = SRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
			
			if(!"Admin".equalsIgnoreCase(admin.getUser_type().toString()))
			{
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(Map.of("message", "Access denied! Admins only."));
			}

			try {
				// Authenticate using Spring Security's AuthenticationManager
				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(username, password));

				if (authentication.isAuthenticated())
				{
					session.setAttribute("LOGGED_IN_ADMIN_ID", admin.getId());
					//auditLogService.saveAuditLog("Admin logged in", admin.getUsername());
					return ResponseEntity.ok(Map.of("message", "Admin Login successful!", "redirectUrl", "/account/dashboard",
							"logged-in-admin", admin));
				}
			} catch (AuthenticationException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed!");

		}
	
	
	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody SellerLogin request) {
//	    String username = request.getUsername();
//	    String password = request.getPassword();
//	    Seller seller = SRepo.findByUsername(username)
//	            .orElseThrow(() -> new RuntimeException("User Not Found"));
//
//	    // Simple password check (not recommended for production)
//	    if (seller.getPassword().equals(password)) {
//	        return ResponseEntity.ok(Map.of("message", "Login Successful!", "Logged-in user", seller));
//	    }
//
//	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
//	}


	
	
	
	
	
	
	
	

	/*
	 * @Autowired private AuthService authService;
	 * 
	 * @PostMapping("/login") public ResponseEntity<String> login(@RequestBody
	 * SellerLogin request) { String response = authService.login(request); return
	 * ResponseEntity.ok(response); }
	 */

	// It is required for csrf token generation.CSRF token is needed for
//	// authentication purpose and more specifically for POST,PUT,DELETE requests
//@GetMapping("/csrf")
//	public CsrfToken csrf(CsrfToken token) {
//		return token;
//	}
}
