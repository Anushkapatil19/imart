package com.API.imart.entities;


import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name="Name")
	private String name;

	@Column
	private String designation;

	@Column(name="primary_email", unique=true, nullable=false)
	private String email;

	@Column(name="username" , unique=true)
	private String username;

	@Column
	private String password;

	@Column(name="primary_mobile")
	private String phone;

	@Column(name="secondary_email", unique=true, nullable=false)
	private String altEmail;

	@Column(name="secondary_mobile")
	private String altPhone;
	
	@Column(name="primary_mob_is_verified")
	private Boolean primary_mob_is_verified;
	
	@Column(name="secondary_mob_is_verified")
	private Boolean secondary_mob_is_verified;
	
	@Column(name="primary_email_is_verified")
	private Boolean primary_email_is_verified;
	
	@Column(name="secondary_email_is_verified")
	private Boolean secondary_email_is_verified;
	
	@Column(name="created_at")
	private LocalDateTime createdat;
	
	@Column(name="updated_at")
	private LocalDateTime updatedat;
	
	public enum user_type {
		Buyer, Seller, Admin, Subadmin
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "user_type")
	private user_type user_type;
	public user_type getUser_type() {
		return user_type;
	}

	public void setUser_type(user_type user_type) {
		this.user_type = user_type;
	}

	
	 @OneToOne(mappedBy="seller",cascade=CascadeType.ALL,orphanRemoval=true)
	 @JsonManagedReference
	 private AddressDetails addressdetails;
	 
	 

	 @OneToOne(mappedBy="seller",cascade=CascadeType.ALL,orphanRemoval=true)
	 @JsonManagedReference
	 private CompanyDetails companydetails;
	   

	 
	 @OneToOne(mappedBy="seller",cascade=CascadeType.ALL,orphanRemoval=true)
	 @JsonManagedReference
	 private BankDetails bankdetails;
	 
	 
	 

	public BankDetails getBankdetails() {
		return bankdetails;
	}

	public void setBankdetails(BankDetails bankdetails) {
		this.bankdetails = bankdetails;
	}

	public CompanyDetails getCompanydetails() {
		return companydetails;
	}

	public void setCompanydetails(CompanyDetails companydetails) {
		this.companydetails = companydetails;
	}

	public AddressDetails getAddressdetails() {
		return addressdetails;
	}

	public void setAddressdetails(AddressDetails addressdetails) {
		this.addressdetails = addressdetails;
	}

	@Lob
	@Column(name = "profile_picture")
	private String ProfilePicture;

	public Seller() {
	}

	public Seller(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}

	public String getAltPhone() {
		return altPhone;
	}

	public void setAltPhone(String altPhone) {
		this.altPhone = altPhone;
	}

	

	public String getProfilePicture() {
		return ProfilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		ProfilePicture = profilePicture;
	}

	public Boolean getPrimary_mob_is_verified() {
		return primary_mob_is_verified;
	}

	public void setPrimary_mob_is_verified(Boolean primary_mob_is_verified) {
		this.primary_mob_is_verified = primary_mob_is_verified;
	}

	public Boolean getSecondary_mob_is_verified() {
		return secondary_mob_is_verified;
	}

	public void setSecondary_mob_is_verified(Boolean secondary_mob_is_verified) {
		this.secondary_mob_is_verified = secondary_mob_is_verified;
	}

	public Boolean getPrimary_email_is_verified() {
		return primary_email_is_verified;
	}

	public void setPrimary_email_is_verified(Boolean primary_email_is_verified) {
		this.primary_email_is_verified = primary_email_is_verified;
	}

	public Boolean getSecondary_email_is_verified() {
		return secondary_email_is_verified;
	}

	public void setSecondary_email_is_verified(Boolean secondary_email_is_verified) {
		this.secondary_email_is_verified = secondary_email_is_verified;
	}

	public LocalDateTime getCreatedat() {
		return createdat;
	}

	public void setCreatedat(LocalDateTime createdat) {
		this.createdat = createdat;
	}

	public LocalDateTime getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(LocalDateTime updatedat) {
		this.updatedat = updatedat;
	}

	
	
	
	

	

	



	
	
	 
	  
	 

}
