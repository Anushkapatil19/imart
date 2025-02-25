package com.API.imart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.API.imart.entities.DocumentVerification;

public interface DocumentVerificationRepository extends JpaRepository<DocumentVerification, Integer> {
	Optional<DocumentVerification> findBySellerId(int sellerId); // ✅ Ensure it returns Optional
}
