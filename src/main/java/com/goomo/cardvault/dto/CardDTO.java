package com.goomo.cardvault.dto;

import java.io.Serializable;

/**
 * This class used to define the meta card details
 * @author Manjunath Jakkandi
 *
 */
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
	private boolean isCardExpired;
	private String cardLabel;
	
	private String cardNumber;
	private String cardExpiryMonthYear;
	private String cardHolderName;
	
	public CardDTO() {
		super();
	}
	
	public CardDTO(String cardNumber, String cardExpiryMonthYear, String cardHolderName) {
		this.cardNumber = cardNumber;
		this.cardExpiryMonthYear = cardExpiryMonthYear;
		this.cardHolderName = cardHolderName;
	}
	
	public CardDTO(String cardNumber, String cardExpiryMonthYear, String cardHolderName, String cardLabel) {
		this.cardNumber = cardNumber;
		this.cardExpiryMonthYear = cardExpiryMonthYear;
		this.cardHolderName = cardHolderName;
		this.cardLabel = cardLabel;
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

	public boolean isCardExpired() {
		return isCardExpired;
	}

	public void setCardExpired(boolean isCardExpired) {
		this.isCardExpired = isCardExpired;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpiryMonthYear() {
		return cardExpiryMonthYear;
	}

	public void setCardExpiryMonthYear(String cardExpiryMonthYear) {
		this.cardExpiryMonthYear = cardExpiryMonthYear;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	
	public String getCardLabel() {
		return cardLabel;
	}

	public void setCardLabel(String cardLabel) {
		this.cardLabel = cardLabel;
	}

	@Override
	public String toString() {
		return "CardDTO [userId=" + userId + ", cardToken=" + cardToken + ", uniqueCardId=" + uniqueCardId
				+ ", cardStatus=" + cardStatus + ", maskedCardNumber=" + maskedCardNumber + ", cardIssuedBy="
				+ cardIssuedBy + ", cardType=" + cardType + ", encCardDetails=" + encCardDetails + ", isCardExpired="
				+ isCardExpired + ", cardLabel=" + cardLabel + ", cardNumber=" + cardNumber + ", cardExpiryMonthYear="
				+ cardExpiryMonthYear + ", cardHolderName=" + cardHolderName + ", toString()=" + super.toString() + "]";
	}
	
}
