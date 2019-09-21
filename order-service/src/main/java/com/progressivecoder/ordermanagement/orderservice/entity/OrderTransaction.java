package com.progressivecoder.ordermanagement.orderservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@JsonInclude(value=Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class OrderTransaction implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "trn_id")
	private Long txnId;

	@Column(name = "trn_unique_id")
	private String txnUniqueId;
	
	@Column(name = "trn_status")
	private String status;
	
	@Column(name = "trn_amount")
	private BigDecimal txnAmount;
	
	@Column(name = "trn_currency")
	private String currency;

	@Column(name = "trn_prod_type")
	private String txnProductType;
	
	@Column(name = "trn_created_date")
	private Date txnCreatedDateAt;
	
	@Column(name = "trn_updated_date")
	private Date txnUpdatedDateAt;

	public Long getTxnId() {
		return txnId;
	}

	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}

	public String getTxnUniqueId() {
		return txnUniqueId;
	}

	public void setTxnUniqueId(String txnUniqueId) {
		this.txnUniqueId = txnUniqueId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTxnProductType() {
		return txnProductType;
	}

	public void setTxnProductType(String txnProductType) {
		this.txnProductType = txnProductType;
	}

	public Date getTxnCreatedDateAt() {
		return txnCreatedDateAt;
	}

	public void setTxnCreatedDateAt(Date txnCreatedDateAt) {
		this.txnCreatedDateAt = txnCreatedDateAt;
	}

	public Date getTxnUpdatedDateAt() {
		return txnUpdatedDateAt;
	}

	public void setTxnUpdatedDateAt(Date txnUpdatedDateAt) {
		this.txnUpdatedDateAt = txnUpdatedDateAt;
	}
	
	
		
}
