package com.API.imart.services;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import com.API.imart.entities.CompanyDetails;

import com.API.imart.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository CRepo;
	
	public CompanyDetails getCompanyDetails(int id)
	{
		return CRepo.findById(id).orElseThrow();
	}
	
	public CompanyDetails createCompanyDetails(CompanyDetails companydetails) 
	{
		return CRepo.save(companydetails);
	}

	public CompanyDetails updateCompanyDetails(CompanyDetails companydetails)
	{
		return CRepo.save(companydetails);
	}
	
	public void deleteCompanyDetails(int id) 
	{
		CRepo.deleteById(id);
	}

}
