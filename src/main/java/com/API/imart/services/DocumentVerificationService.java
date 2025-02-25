package com.API.imart.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.API.imart.entities.DocumentVerification;
import com.API.imart.entities.DocumentVerification.DocumentStatus;
import com.API.imart.entities.Seller;
import com.API.imart.repository.DocumentVerificationRepository;
import com.API.imart.repository.SellerRepository;

@Service
public class DocumentVerificationService {

	@Autowired
	private DocumentVerificationRepository docRepo;

	@Autowired
	private SellerRepository sellerRepo;

	// ✅ Upload or Update Documents
	public DocumentVerification uploadDocuments(int sellerId, DocumentVerification newDocs) {
		Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));

		DocumentVerification existingDocs = docRepo.findBySellerId(sellerId).orElseGet(DocumentVerification::new);

		existingDocs.setSeller(seller);
		existingDocs.setUdyamAadhar(newDocs.getUdyamAadhar());
		existingDocs.setFssai(newDocs.getFssai());
		existingDocs.setShopAct(newDocs.getShopAct());
		existingDocs.setPan(newDocs.getPan());
		existingDocs.setGstin(newDocs.getGstin());
		existingDocs.setStatus(DocumentStatus.PENDING); // Reset to PENDING for verification

		return docRepo.save(existingDocs);
	}

	// ✅ Get Seller Documents
	public DocumentVerification getSellerDocuments(int sellerId) {
		return docRepo.findBySellerId(sellerId).orElseThrow(() -> new RuntimeException("Documents not found"));
	}

	// ✅ Admin Verification
	public DocumentVerification verifyDocuments(int docId, DocumentStatus status, String remarks) {
		DocumentVerification docs = docRepo.findById(docId)
				.orElseThrow(() -> new RuntimeException("Documents not found"));

		docs.setStatus(status);
		docs.setAdminRemarks(remarks);
		return docRepo.save(docs);
	}
}
