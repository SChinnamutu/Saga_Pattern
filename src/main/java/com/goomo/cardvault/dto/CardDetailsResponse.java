package com.goomo.cardvault.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This class used to generate the response for card details search by token, card unique id and user id.
 * @author Manjunath Jakkandi
 *
 */
public class CardDetailsResponse extends BaseResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;
	private String token;
	private String cardUniqueId;
	private CardDTO card;
	private List<CardDTO> cardList;
	private String cckeyPart2;
	private String tokenExpiry;
	private String cardType;
	private String cardIssuedBy;
	private String cckeyPart1;
	private String cardDetails;
	private String cardNumber;
	private String cardHolderName;
	private String cardExpiryDate;
	
	public CardDetailsResponse() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCardUniqueId() {
		return cardUniqueId;
	}

	public void setCardUniqueId(String cardUniqueId) {
		this.cardUniqueId = cardUniqueId;
	}

	public CardDTO getCard() {
		return card;
	}

	public void setCard(CardDTO card) {
		this.card = card;
	}

	public List<CardDTO> getCardList() {
		return cardList;
	}

	public void setCardList(List<CardDTO> cardList) {
		this.cardList = cardList;
	}
	
	public String getCckeyPart2() {
		return cckeyPart2;
	}

	public void setCckeyPart2(String cckeyPart2) {
		this.cckeyPart2 = cckeyPart2;
	}
	
	public String getTokenExpiry() {
		return tokenExpiry;
	}

	public void setTokenExpiry(String tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
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
	
	public String getCckeyPart1() {
		return cckeyPart1;
	}

	public void setCckeyPart1(String cckeyPart1) {
		this.cckeyPart1 = cckeyPart1;
	}
	
	public String getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(String cardDetails) {
		this.cardDetails = cardDetails;
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
		return "CardDetailsResponse [userId=" + userId + ", token=" + token + ", cardUniqueId=" + cardUniqueId
				+ ", card=" + card + ", cardList=" + cardList + ", cckeyPart2=" + cckeyPart2 + ", tokenExpiry="
				+ tokenExpiry + ", cardType=" + cardType + ", cardIssuedBy=" + cardIssuedBy + ", cckeyPart1="
				+ cckeyPart1 + ", getStatus()=" + getStatus() + ", getStatusMessage()=" + getStatusMessage()
				+ ", toString()=" + super.toString() + "]";
	}

}
