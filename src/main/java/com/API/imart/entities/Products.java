package com.API.imart.entities;

import java.time.LocalDateTime;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Products {
	
	

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column
		private int id;
		
		
		
		@Column(name = "prod_name")
		private String prodname;
		
		@Column
		private Double price;
		
		@Column(name= "description")
		private String description;
		
		
		
		@Column(name = "productimage")
		private String imagepath;
		
		@Column(name= "weight")
		private double weight;
		
		
		
		
		@Column(name="brand", length = 255)
	    private String brand;
		
		@Column(name= "stock_keeping_unit" , unique = true)
	    private String sku;
		
		@Column(name= "discount")
		private Double discount; // Discount percentage (0-100)

	    @Column(name = "discount_start")
	    private LocalDateTime discountStart; // Discount start date/time

	    @Column(name = "discount_end")
	    private LocalDateTime discountEnd; // Discount end date/time

	    
	    @Column(name = "created_at")
	    private LocalDateTime createdAt; // Record creation date/time

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt; // Record update date/time

	    
//	    @Enumerated(EnumType.STRING)
//	    @Column(length = 10)
//	    private Status status; // Product status (ACTIVE/INACTIVE)
//	    public enum Status {
//	        ACTIVE,
//	        INACTIVE
//	    }

	    
	    @Column(name="is_available")
	    private Boolean is_available;
	    
	    @Column(name = "is_deleted")
	    private Boolean deletedIs = false; // Soft delete flag

	    @Column(name = "deleted_at")
	    private LocalDateTime deletedAt; // Deletion timestamp

	    @Column(name = "deleted_by", length = 255)
	    private String deletedBy;
	    
	    @Column(name ="material")
	    private String material;
	    
	    @Column(name="quantity_per_pack")
		private Integer quantity_per_pack;
		
		@Column(name = "packaging_type") 
	    private String packagingtype;
		
		public enum PackagingSize {
	        SMALL, MEDIUM, LARGE
	    }
		 @Enumerated(EnumType.STRING)
		 @Column(name = "packaging_size")
		 private PackagingSize packagingsize;
		
		 @Column(name = "pack_types_available")
		 private String pack_types_available;
		
		 @Column(name = "texture")
		 private String texture;
		
		 @Column(name = "delivery_time") 
		 @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
		 private LocalDateTime delivery_time;
		
		@Column(name="service_code")
		 private Integer service_code;
		 
		 @Column(name="is_verified")
		 private Boolean is_verified; 
		
		 @Column(name="rating")
		 private Double rating;
		 
		 @Column(name = "shelf_life") 
		 private String shelflife;
		 
		 @Column(name="minimum_order_qty")
		 private Integer minimum_order_qty;
		 
		 
		  
		 public enum category_type {
			    ELECTRONICS,
			    AUTOMOTIVE,
			    INDUSTRIAL_EQUIPMENT,
			    MEDICAL_SUPPLIES,
			    CONSTRUCTION_MATERIALS,
			    AGRICULTURE,
			    TEXTILES,
			    OFFICE_SUPPLIES,
			    FOOD_BEVERAGE,
			    CHEMICALS,
			    PACKAGING,
			    BEAUTY_PERSONAL_CARE,
			    HOME_APPLIANCES,
			    IT_SOFTWARE,
			    SECURITY_SAFETY,
			    TRANSPORTATION_LOGISTICS,
			    ENERGY_UTILITIES,
			    STATIONERY
			}
		
		 @Enumerated(EnumType.STRING) // Stores the enum as a string in the database
		    @Column(name = "category_type",columnDefinition = "ENUM('ELECTRONICS', 'AUTOMOTIVE', 'INDUSTRIAL_EQUIPMENT', 'MEDICAL_SUPPLIES', " +
		            "'CONSTRUCTION_MATERIALS', 'AGRICULTURE', 'TEXTILES', 'OFFICE_SUPPLIES', 'FOOD_BEVERAGE', 'CHEMICALS', " +
		            "'PACKAGING', 'BEAUTY_PERSONAL_CARE', 'HOME_APPLIANCES', 'IT_SOFTWARE', 'SECURITY_SAFETY', " +
		            "'TRANSPORTATION_LOGISTICS', 'ENERGY_UTILITIES', 'STATIONERY')")
		 private category_type category_type;
		 
		 

	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "category_id", nullable = false)
	    @JsonBackReference
	    private Category category;
	    
	    @ManyToOne
	    @JoinColumn(name = "user_id")
		/* @JsonBackReference */
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

		public String getProdname() {
			return prodname;
		}

		public void setProdname(String prodname) {
			this.prodname = prodname;
		}
		
		

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		

		
		public String getImagepath() {
			return imagepath;
		}

		public void setImagepath(String imagepath) {
			this.imagepath = imagepath;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public String getSku() {
			return sku;
		}

		public void setSku(String sku) {
			this.sku = sku;
		}

		public Double getDiscount() {
			return discount;
		}

		public void setDiscount(Double discount) {
			this.discount = discount;
		}

		public LocalDateTime getDiscountStart() {
			return discountStart;
		}

		public void setDiscountStart(LocalDateTime discountStart) {
			this.discountStart = discountStart;
		}

		public LocalDateTime getDiscountEnd() {
			return discountEnd;
		}

		public void setDiscountEnd(LocalDateTime discountEnd) {
			this.discountEnd = discountEnd;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		
		public Boolean getIs_available() {
			return is_available;
		}

		public void setIs_available(Boolean is_available) {
			this.is_available = is_available;
		}

		public Boolean getDeletedIs() {
			return deletedIs;
		}

		public void setDeletedIs(Boolean deletedIs) {
			this.deletedIs = deletedIs;
		}

		public LocalDateTime getDeletedAt() {
			return deletedAt;
		}

		public void setDeletedAt(LocalDateTime deletedAt) {
			this.deletedAt = deletedAt;
		}

		public String getDeletedBy() {
			return deletedBy;
		}

		public void setDeletedBy(String deletedBy) {
			this.deletedBy = deletedBy;
		} // Who deleted the record

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}

		public String getMaterial() {
			return material;
		}

		public void setMaterial(String material) {
			this.material = material;
		}

		public Integer getQuantity_per_pack() {
			return quantity_per_pack;
		}

		public void setQuantity_per_pack(Integer quantity_per_pack) {
			this.quantity_per_pack = quantity_per_pack;
		}

		public String getPackagingtype() {
			return packagingtype;
		}

		public void setPackagingtype(String packagingtype) {
			this.packagingtype = packagingtype;
		}

		public PackagingSize getPackagingsize() {
			return packagingsize;
		}

		public void setPackagingsize(PackagingSize packagingsize) {
			this.packagingsize = packagingsize;
		}

		public String getPack_types_available() {
			return pack_types_available;
		}

		public void setPack_types_available(String pack_types_available) {
			this.pack_types_available = pack_types_available;
		}

		public String getTexture() {
			return texture;
		}

		public void setTexture(String texture) {
			this.texture = texture;
		}

		public LocalDateTime getDelivery_time() {
			return delivery_time;
		}

		public void setDelivery_time(LocalDateTime delivery_time) {
			this.delivery_time = delivery_time;
		}

		public Boolean getIs_verified() {
			return is_verified;
		}

		public void setIs_verified(Boolean is_verified) {
			this.is_verified = is_verified;
		}

		public Double getRating() {
			return rating;
		}

		public void setRating(Double rating) {
			this.rating = rating;
		}

		public String getShelflife() {
			return shelflife;
		}

		public void setShelflife(String shelflife) {
			this.shelflife = shelflife;
		}

		public Integer getMinimum_order_qty() {
			return minimum_order_qty;
		}

		public void setMinimum_order_qty(Integer minimum_order_qty) {
			this.minimum_order_qty = minimum_order_qty;
		}

		public Integer getService_code() {
			return service_code;
		}

		public void setService_code(Integer service_code) {
			this.service_code = service_code;
		}

		public category_type getCategory_type() {
			return category_type;
		}

		public void setCategory_type(category_type category_type) {
			this.category_type = category_type;
		}
		
		
		
	}
		


