package com.API.imart.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data

public class CompanyDetails {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyName;       // Name of the company
    @Column(name="company_website")
    private String website;           // Official website
    private String gstin;             // GST Identification Number
    private String instagramHandle;   // Instagram profile link
    private String facebookHandle;    // Facebook profile link
    private String pan;
	
       @ManyToOne
	   @JoinColumn(name ="user_id")
	   @JsonBackReference
	   private Seller seller;

    
    
    
    
    public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	public String getInstagramHandle() {
		return instagramHandle;
	}
	public void setInstagramHandle(String instagramHandle) {
		this.instagramHandle = instagramHandle;
	}
	public String getFacebookHandle() {
		return facebookHandle;
	}
	public void setFacebookHandle(String facebookHandle) {
		this.facebookHandle = facebookHandle;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	} 
    
    
    
}
