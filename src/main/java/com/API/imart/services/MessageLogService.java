package com.API.imart.services;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.API.imart.entities.MessageLog;
import com.API.imart.repository.MessageLogRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageLogService {

	
	
		@Autowired 
		MessageLogRepository msglogrepo;
		 @Transactional
		public MessageLog saveMessage(int buyerId,int sellerId,String message)
		{
			MessageLog msg=new MessageLog(message,buyerId,sellerId,LocalDateTime.now());
			return msglogrepo.save(msg);
			
		}
		 public ResponseEntity<?> getMessages(int buyerId, int sellerId) {
			 List<MessageLog> result=msglogrepo.findByBuyerIdAndSellerId(buyerId, sellerId);
			 if(result.isEmpty())
			 {
				  return ResponseEntity.ok("No messages");

			 }
			 else
			 {
			        return ResponseEntity.ok(result);

			 }
		    }
		 
		 
		 
	}

