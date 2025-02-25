package com.API.imart.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.API.imart.entities.MessageLog;
import com.API.imart.services.AuditLogService;
import com.API.imart.services.MessageLogService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageLogService messagelogservice;

    @Autowired
    private AuditLogService auditLogService;

    // Send Message
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> request, HttpSession session) {
        try {
            Integer loggedInUserId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");
            if (loggedInUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "User not authenticated"));
            }

            int buyerId = Integer.parseInt(request.get("buyerId"));
            int sellerId = Integer.parseInt(request.get("sellerId"));
            String message = request.get("message");

            // Ensure logged-in user is either buyer or seller to prevent ID spoofing
            if (loggedInUserId != buyerId && loggedInUserId != sellerId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "Unauthorized: You cannot send messages on behalf of others"));
            }

            MessageLog msglog = messagelogservice.saveMessage(buyerId, sellerId, message);

            // Audit Log
            auditLogService.saveAuditLog("Message Sent", 
                "User ID: " + loggedInUserId + " sent a message from Buyer ID: " + buyerId + " to Seller ID: " + sellerId);

            return ResponseEntity.ok(Map.of("message", "Message sent successfully", "data", msglog));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error processing message", "error", e.getMessage()));
        }
    }

    // Get chat history between a buyer and seller
    @GetMapping("/history")
    public ResponseEntity<?> getMessageHistory(@RequestParam int buyerId, @RequestParam int sellerId) {
        return messagelogservice.getMessages(buyerId, sellerId);
    }

    // Get messages for the logged-in user
    @GetMapping("/myMessages")
    public ResponseEntity<?> getMyMessages(@RequestParam int sellerId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("LOGGED_IN_USER_ID");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "User not authenticated"));
        }

        return messagelogservice.getMessages(userId, sellerId);
    }
}
