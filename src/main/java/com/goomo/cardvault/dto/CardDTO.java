package com.goomo.cardvault.dto;

import java.io.Serializable;

public class CardDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;
	private String cardToken;
	private String uniqueCardId;
	private String cardStatus;
	private String maskedCardNumber;
	private String cardIssuedBy;
	private String cardType;
	private String encCardDetails;
	
	public CardDTO() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getMaskedCardNumber() {
		return maskedCardNumber;
	}

	public void setMaskedCardNumber(String maskedCardNumber) {
		this.maskedCardNumber = maskedCardNumber;
	}

	public String getCardIssuedBy() {
		return cardIssuedBy;
	}

	public void setCardIssuedBy(String cardIssuedBy) {
		this.cardIssuedBy = cardIssuedBy;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getEncCardDetails() {
		return encCardDetails;
	}

	public void setEncCardDetails(String encCardDetails) {
		this.encCardDetails = encCardDetails;
	}
	
}
