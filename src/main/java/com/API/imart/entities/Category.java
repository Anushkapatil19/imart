package com.API.imart.entities;


import java.time.LocalDateTime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;





@Entity
@Table(name = "category")
public class Category 
{
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column
	    private int id;
	    
	    @Column(name = "category_name")
	    private String categoryName;
	    
	    @Column(name= "description")
		private String description;
	    
	    @Column(name = "created_at")
	    private LocalDateTime createdAt; // Record creation date/time

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt; // Record update date/time
	    
	    @Column(name = "is_deleted")
	    private Boolean deletedIs = false; // Soft delete flag

	    @Column(name = "deleted_at")
	    private LocalDateTime deletedAt; // Deletion timestamp

	    @Column(name = "deleted_by", length = 255)
	    private Integer deletedBy;
	    
	    @Column(name = "created_by", length = 255)
	    private String createdBy;
	    
	    @Column(name= "specification")
	    private String specification;
	    
	    @Column(name= "highlighted_points")
	    private String highlighted_points;
	    
	    @Column(name= "info_file")
	    private String info_file;
	    
	    @Column(name= "excel_sheet")
	    private String excel_sheet;
	    
	    @Column(name= "category_image_url")
	    private String category_image_url;
	    
	    @Column(name= "origin")
	    private String origin;
	    
	    
	    
	  //Foreign key which relate product and category table
	    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private List<Products> product;
	    
	    @ManyToOne
	    @JoinColumn(name = "user_id")
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

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryname) {
			this.categoryName = categoryname;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
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

		public Integer getDeletedBy() {
			return deletedBy;
		}

		public void setDeletedBy(Integer deletedBy) {
			this.deletedBy = deletedBy;
		}

		public List<Products> getProduct() {
			return product;
		}

		public void setProduct(List<Products> products) {
			this.product = products;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public String getSpecification() {
			return specification;
		}

		public void setSpecification(String specification) {
			this.specification = specification;
		}

		public String getHighlighted_points() {
			return highlighted_points;
		}

		public void setHighlighted_points(String highlighted_points) {
			this.highlighted_points = highlighted_points;
		}

		public String getInfo_file() {
			return info_file;
		}

		public void setInfo_file(String info_file) {
			this.info_file = info_file;
		}

		public String getExcel_sheet() {
			return excel_sheet;
		}

		public void setExcel_sheet(String excel_sheet) {
			this.excel_sheet = excel_sheet;
		}

		public String getCategory_image_url() {
			return category_image_url;
		}

		public void setCategory_image_url(String category_image_url) {
			this.category_image_url = category_image_url;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		
	    
}
