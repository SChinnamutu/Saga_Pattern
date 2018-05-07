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

	@Override
	public String toString() {
		return "CardDetailsResponse [userId=" + userId + ", token=" + token + ", cardUniqueId=" + cardUniqueId
				+ ", card=" + card + ", cardList=" + cardList + ", cckeyPart2=" + cckeyPart2 + ", tokenExpiry="
				+ tokenExpiry + ", cardType=" + cardType + ", cardIssuedBy=" + cardIssuedBy + ", getStatus()="
				+ getStatus() + ", getStatusMessage()=" + getStatusMessage() + ", toString()=" + super.toString() + "]";
	}

}
