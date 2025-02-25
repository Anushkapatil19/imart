package com.API.imart.repository;

import java.util.List;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.API.imart.entities.Category;
import com.API.imart.entities.Seller;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findBySeller(Seller seller);
	 Optional<Category> findBycategoryName(String name);
}
