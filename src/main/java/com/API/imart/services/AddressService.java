package com.API.imart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.API.imart.entities.AddressDetails;
import com.API.imart.repository.AddressRepository;

import jakarta.transaction.Transactional;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository ARepo;
	
	public AddressDetails getAddressDetails(int id)
	{
		return ARepo.findById(id).orElseThrow();
	}
	
	public AddressDetails createAddressDetails(AddressDetails addressdetails) 
	{
		return ARepo.save(addressdetails);
	}

	public AddressDetails updateAddressDetails(AddressDetails addressdetails)
	{
		return ARepo.save(addressdetails);
	}
	
	    @Transactional
	    public String deleteAddress(Integer sellerId) {
	        // ✅ Fetch the address linked to the seller
	        AddressDetails existingAddress = ARepo.findBySellerId(sellerId);

	        if (existingAddress == null) {
	            throw new RuntimeException("No address found for seller ID: " + sellerId);
	        }

	        // ✅ Delete the address
	        ARepo.delete(existingAddress);

	        return "✅ Address deleted successfully!";
	    }
	}

	

