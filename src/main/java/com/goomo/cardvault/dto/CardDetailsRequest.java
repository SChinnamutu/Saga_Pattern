package com.goomo.cardvault.dto;

import java.io.Serializable;

/**
 * This class used to define the parameters required for card vault API's.
 * @author Manjunath Jakkandi
 *
 */
public class CardDetailsRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String cardToken;
	private String cardUniqueId;
	private String encCardDetails;
	private String txnBy;
	private boolean pemKey;
	private String pesToken;
	private String cmk;
	private String cardLabel;
	private String cckeyPart2;
	private String cardNumber;
	private String cardHolderName;
	private String cardExpiryDate;
	
	public CardDetailsRequest() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardUniqueId() {
		return cardUniqueId;
	}

	public void setCardUniqueId(String cardUniqueId) {
		this.cardUniqueId = cardUniqueId;
	}
	
	public String getEncCardDetails() {
		return encCardDetails;
	}

	public void setEncCardDetails(String encCardDetails) {
		this.encCardDetails = encCardDetails;
	}

	public String getTxnBy() {
		return txnBy;
	}

	public void setTxnBy(String txnBy) {
		this.txnBy = txnBy;
	}

	public boolean isPemKey() {
		return pemKey;
	}

	public void setPemKey(boolean pemKey) {
		this.pemKey = pemKey;
	}

	public String getCardToken() {
		return cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	public String getPesToken() {
		return pesToken;
	}

	public void setPesToken(String pesToken) {
		this.pesToken = pesToken;
	}
	
	public String getCmk() {
		return cmk;
	}

	public void setCmk(String cmk) {
		this.cmk = cmk;
	}
	
	public String getCardLabel() {
		return cardLabel;
	}

	public void setCardLabel(String cardLabel) {
		this.cardLabel = cardLabel;
	}
	
	public String getCckeyPart2() {
		return cckeyPart2;
	}

	public void setCckeyPart2(String cckeyPart2) {
		this.cckeyPart2 = cckeyPart2;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	@Override
	public String toString() {
		return "CardDetailsRequest [userId=" + userId + ", cardToken=" + cardToken + ", cardUniqueId=" + cardUniqueId
				+ ", encCardDetails=" + encCardDetails + ", txnBy=" + txnBy + ", pemKey=" + pemKey + ", pesToken="
				+ pesToken + ", cmk=" + cmk + ", cardLabel=" + cardLabel + ", cckeyPart2=" + cckeyPart2+ "]";
	}

}
