package com.goomo.cardvault.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class used to represent card master table in database.
 * @author Manjunath Jakkandi
 *
 */

@Entity
@Table(name = "card_master")
public class CardMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "card_id")
	private Long cardId;
	
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cardMaster", cascade = CascadeType.ALL)
	private List<CCKeyMaster> cckeyMasterList;
	
	
	@Column(name = "card_token")
	private String cardToken;
	
	@Column(name = "card_unique_id")
	private String uniqueCardId;
	
	@Column(name = "card_enc_details")
	private String encryptedCardDetails;
	
	@Column(name = "masked_card_number")
	private String maskedCardNumber;
	
	@Column(name = "card_type")
	private String cardType;
	
	@Column(name = "card_issued_by")
	private String cardIssuedBy;
	
	@Column(name = "card_status")
	private String cardStatus;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_at")
	private String createdAt;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated_at")
	private String updatedAt;
	
	public CardMaster() {
		super();
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getCardToken() {
		return cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	public String getUniqueCardId() {
		return uniqueCardId;
	}

	public void setUniqueCardId(String uniqueCardId) {
		this.uniqueCardId = uniqueCardId;
	}

	public String getEncryptedCardDetails() {
		return encryptedCardDetails;
	}

	public void setEncryptedCardDetails(String encryptedCardDetails) {
		this.encryptedCardDetails = encryptedCardDetails;
	}

	public String getMaskedCardNumber() {
		return maskedCardNumber;
	}

	public void setMaskedCardNumber(String maskedCardNumber) {
		this.maskedCardNumber = maskedCardNumber;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardIssuedBy() {
		return cardIssuedBy;
	}

	public void setCardIssuedBy(String cardIssuedBy) {
		this.cardIssuedBy = cardIssuedBy;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<CCKeyMaster> getCckeyMasterList() {
		return cckeyMasterList;
	}

	public void setCckeyMasterList(List<CCKeyMaster> cckeyMasterList) {
		this.cckeyMasterList = cckeyMasterList;
	}
	
	
	
}
