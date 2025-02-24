package com.API.imart.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
	
	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    private String action;
	    private String username;
	    private LocalDateTime timestamp;
	    
	    
	    @Column(name = "seller_id")  // Make sure the column exists in the DB
	    private Integer sellerId;
	   
	    
	    public AuditLog() {
	        this.timestamp = LocalDateTime.now(); // Set current timestamp automatically
	    }
	    
	   
	    
	    public AuditLog(String action, String username) {
	        this.action = action;
	        this.username = username;
	        this.timestamp = LocalDateTime.now();
	        
	    }
	    
	    public int getId() {
	        return id;
	    }

	    public String getAction() {
	        return action;
	    }

	    public void setAction(String action) {
	        this.action = action;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public LocalDateTime getTimestamp() {
	        return timestamp;
	    }

	    public void setTimestamp(LocalDateTime timestamp) {
	        this.timestamp = timestamp;
	    }



		public Integer getSellerId() {
			return sellerId;
		}



		public void setSellerId(Integer sellerId) {
			this.sellerId = sellerId;
		}



		

		


		
	    
	

}
