package com.API.imart.services;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import com.API.imart.entities.SellerLogin;
import com.API.imart.entities.Seller;
import com.API.imart.repository.SellerRepository;

@Service
public class AuthService {

	@Autowired
	private SellerRepository SRepo;

	
 
    public String login(SellerLogin request) {
        Seller user = SRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return "Login successful for user: " + user.getUsername();
    }

}
