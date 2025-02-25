package com.API.imart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.API.imart.entities.BankDetails;
import com.API.imart.repository.BankRepository;

@Service
public class BankService 
{
	@Autowired
	private BankRepository BRepo;
	
	public BankDetails getBankDetails(int id)
	{
		return BRepo.findById(id).orElseThrow();
	}
	
	public BankDetails createBankDetails(BankDetails bankdetails) 
	{
		return BRepo.save(bankdetails);
	}

	public BankDetails updateBankDetails(BankDetails bankdetails)
	{
		return BRepo.save(bankdetails);
	}
	
	public void deleteBankDetails(int id) 
	{
		BRepo.deleteById(id);
	}

	
	
   
}
