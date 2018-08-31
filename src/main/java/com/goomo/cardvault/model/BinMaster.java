package com.goomo.cardvault.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This model sued to store or fetch bin details of card. The details are pre-stored in database. And it's an incremental update through offline
 * process. We will use this model to fetch the details of bin lookup during store new card and respective details are stored in bin_master itself
 * to de-normalizing the data.
 * @author Manjunath Jakkandi
 *
 */

@Entity
@Table(name = "bin_master")
public class BinMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bin_id")
	private Long binId;
	
	@Column(name = "bin_val")
	private String binVal;
	
	@Column(name = "card_type")
	private String cardType;
	
	@Column(name = "brand_type")
	private String brandType;
	
	@Column(name = "card_issued_by")
	private String cardIssuedBy;
	
	@Column(name = "country_id")
	private String countryId;
	
	@Column(name = "created_at")
	private String createdAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_at")
	private String updatedAt;
	
	@Column(name = "updated_by")
	private String updatedBy;

	public Long getBinId() {
		return binId;
	}

	public void setBinId(Long binId) {
		this.binId = binId;
	}

	public String getBinVal() {
		return binVal;
	}

	public void setBinVal(String binVal) {
		this.binVal = binVal;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public String getCardIssuedBy() {
		return cardIssuedBy;
	}

	public void setCardIssuedBy(String cardIssuedBy) {
		this.cardIssuedBy = cardIssuedBy;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}	 	

}
