package com.API.imart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.API.imart.entities.AuditLog;
import com.API.imart.services.AuditLogService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {
	
	
	    
	    private final AuditLogService auditLogService;
	    
	    public AuditLogController(AuditLogService auditLogService) {
	        this.auditLogService = auditLogService;
	    }

	    @GetMapping("/recentActivities")
	    public ResponseEntity<?> getRecentActivities(HttpSession session) {
	        // Retrieve seller ID from session
	        Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

	        // Debugging logs
	        System.out.println("Session ID: " + session.getId());
	        System.out.println("Session Seller ID: " + sellerId);

	        // If session is missing seller ID, return 401 Unauthorized
	        if (sellerId == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(Map.of("message", "Unauthorized: Please log in."));
	        }

	        // Fetch only activities for the logged-in seller
	        List<AuditLog> auditLogs = auditLogService.getRecentActivitiesForSeller(sellerId);

	        // If no activities found, return an empty response
	        if (auditLogs.isEmpty()) {
	            return ResponseEntity.ok(Map.of("message", "No recent activities found.", "data", List.of()));
	        }

	        return ResponseEntity.ok(Map.of("message", "Recent activities retrieved successfully.", "data", auditLogs));
	    }





	    @DeleteMapping("/delete-old")
	    public ResponseEntity<?> deleteOldActivities(HttpSession session) {
	        // Retrieve user ID from session
	        Object sellerId = session.getAttribute("LOGGED_IN_USER_ID");

	        // Debugging logs
	        System.out.println("Session ID: " + session.getId());
	        System.out.println("Session User ID: " + sellerId);

	        // If session is missing user ID, return 401 Unauthorized
	        if (sellerId == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized: No user session. Please log in."));
	        }

	        // Perform deletion of old activities
	        auditLogService.deleteOldActivities();
	        
	        return ResponseEntity.ok(Map.of("message", "Old activities deleted successfully"));
	    }

	}


