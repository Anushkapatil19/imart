package com.API.imart.repository;

import java.util.List;


 

import org.springframework.data.jpa.repository.JpaRepository;
import com.API.imart.entities.Products;
import com.API.imart.entities.Seller;



public interface ProductRepository extends JpaRepository<Products, Integer>{
	
	//Code to fetch products of specific category id
	List<Products> findByCategoryId(int categoryId);
	
	//Code to fetch products of specific seller id
	List<Products> findBySellerId(int sellerId);
	
	
	List<Products> findBySeller(Seller seller);

}


