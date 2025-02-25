package com.API.imart.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.API.imart.entities.BankDetails;


public interface BankRepository extends JpaRepository<BankDetails, Integer>{
	 BankDetails findBySellerId(Integer sellerId);

}
