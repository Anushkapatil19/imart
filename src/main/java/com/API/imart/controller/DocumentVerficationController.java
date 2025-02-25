package com.API.imart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.API.imart.entities.DocumentVerification;
import com.API.imart.entities.DocumentVerification.DocumentStatus;
import com.API.imart.services.AuditLogService;
import com.API.imart.services.DocumentVerificationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/seller/documents")
public class DocumentVerficationController {

	@Autowired
	private DocumentVerificationService docService;
	@Autowired
	private AuditLogService auditLogService;

	// ✅ Upload or Update Documents (Session-Based)
	@PostMapping("/upload")
	public ResponseEntity<?> uploadDocuments(@RequestBody DocumentVerification documents, HttpSession session) {
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first.");
		}

		DocumentVerification savedDocs = docService.uploadDocuments(sellerId, documents);
		// Audit log for document upload
		auditLogService.saveAuditLog("Document Uploaded", "Seller ID: " + sellerId + " uploaded documents.");
		return ResponseEntity.ok(savedDocs);
	}

	// ✅ Get Documents (Session-Based)
	@GetMapping
	public ResponseEntity<?> getDocuments(HttpSession session) {
		Integer sellerId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
		if (sellerId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first.");
		}

		DocumentVerification docs = docService.getSellerDocuments(sellerId);
		return ResponseEntity.ok(docs);
	}

	// ✅ Admin: Verify Documents
	@PutMapping("/verify/{docId}")
	public ResponseEntity<?> verifyDocuments(@PathVariable int docId, @RequestParam DocumentStatus status,
			@RequestParam(required = false) String remarks, HttpSession session) {
		try {
			// Ensure only admin users can verify
			Integer adminId = (Integer) session.getAttribute("LOGGED_IN_ADMIN_ID");
			if (adminId == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("Access denied. Only admins can verify documents.");
			}

			DocumentVerification verifiedDocs = docService.verifyDocuments(docId, status, remarks);

			// Audit log for document verification
			auditLogService.saveAuditLog("Document Verified",
					"Admin ID: " + adminId + " verified Document ID: " + docId + " with status: " + status);

			return ResponseEntity.ok(verifiedDocs);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error verifying document: " + e.getMessage());
		}
	}
}
