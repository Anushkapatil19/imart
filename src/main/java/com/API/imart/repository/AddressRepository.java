package com.API.imart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.API.imart.entities.AddressDetails;
public interface AddressRepository extends JpaRepository<AddressDetails, Integer>{
	AddressDetails findBySellerId(int sellerId);
}
