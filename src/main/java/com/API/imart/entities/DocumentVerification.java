package com.API.imart.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "verification_documents")
public class DocumentVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@OneToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Seller seller;

	private String udyamAadhar;
	private String fssai;
	private String shopAct;
	private String pan;
	private String gstin;

	@Enumerated(EnumType.STRING)
	private DocumentStatus status = DocumentStatus.PENDING;

	public enum DocumentStatus {
		PENDING, // Default status when a seller uploads documents
		APPROVED, // Admin approves documents
		REJECTED // Admin rejects documents
	}

	private String adminRemarks;

	// ✅ Default Constructor
	public DocumentVerification() {
	}

	// ✅ Parameterized Constructor
	public DocumentVerification(Seller seller, String udyamAadhar, String fssai, String shopAct, String pan,
			String gstin, DocumentStatus status) {
		this.seller = seller;
		this.udyamAadhar = udyamAadhar;
		this.fssai = fssai;
		this.shopAct = shopAct;
		this.pan = pan;
		this.gstin = gstin;
		this.status = status;
	}

	// ✅ Getters & Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getUdyamAadhar() {
		return udyamAadhar;
	}

	public void setUdyamAadhar(String udyamAadhar) {
		this.udyamAadhar = udyamAadhar;
	}

	public String getFssai() {
		return fssai;
	}

	public void setFssai(String fssai) {
		this.fssai = fssai;
	}

	public String getShopAct() {
		return shopAct;
	}

	public void setShopAct(String shopAct) {
		this.shopAct = shopAct;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public DocumentStatus getStatus() {
		return status;
	}

	public void setStatus(DocumentStatus status) {
		this.status = status;
	}

	// ✅ Add Getter and Setter
	public String getAdminRemarks() {
		return adminRemarks;
	}

	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
	}
}
