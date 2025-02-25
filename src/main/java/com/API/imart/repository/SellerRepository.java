package com.API.imart.repository;


import com.API.imart.entities.Seller;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SellerRepository extends JpaRepository<Seller, Integer>{
	Optional<Seller> findByUsername(String username);


	Optional<Seller> findById(int id);
	
	 //@Query("SELECT s FROM Seller s JOIN FETCH s.addressdetails WHERE s.id=:id")
	    //Seller findSellerWithAddressDetails(@Param("id") int id);
	 

	//@Query("SELECT s FROM Seller s JOIN FETCH s.companydetails WHERE s.id=:id")
	    //Seller findSellerWithCompanyDetails(@Param("id") int id);
	
	  @Query("SELECT s FROM Seller s JOIN FETCH s.bankdetails WHERE s.id=:id")
	    Seller findSellerWithBankDetails(@Param("id") int id);
	
}

	

