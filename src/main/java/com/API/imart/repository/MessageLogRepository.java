package com.API.imart.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.API.imart.entities.MessageLog;

public interface MessageLogRepository extends JpaRepository<MessageLog,Integer> {
	
	
	

	
		List<MessageLog> findBySellerId(int sellerId);
	    List<MessageLog> findByBuyerIdAndSellerId(int buyerId, int sellerId);

	}


