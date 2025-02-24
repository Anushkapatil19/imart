package com.API.imart.entities;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="message_log")
public class MessageLog {
	
	

	
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column
	private int id;	
	@Column
	private String messageContent;
	@Column
	private int buyerId;
	@Column
	private int sellerId;
	@Column
	private LocalDateTime createdAt;
	public MessageLog() {
	}

	public MessageLog(String messageContent, int buyerId, int sellerId,LocalDateTime createdAt) {
		super();
		this.messageContent = messageContent;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.createdAt= createdAt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/*
	 * public LocalDateTime getCreatedAt() { return createdAt; } public void
	 * setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	 */


	}

