package com.API.imart.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.API.imart.entities.CompanyDetails;

public interface CompanyRepository extends JpaRepository<CompanyDetails, Integer>{
	 CompanyDetails findBySellerId(Integer sellerId);
}


