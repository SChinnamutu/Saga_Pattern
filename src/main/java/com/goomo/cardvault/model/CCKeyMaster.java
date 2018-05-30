package com.goomo.cardvault.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * This class used to represent cckey master table in database. encrypted keys are stored here. These keys required to decrypt card details.
 * @author Manjunath Jakkandi
 *
 */

@Entity
@Table(name = "cckey_master")
public class CCKeyMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cckey_id")
	private Long cckeyId;
	
	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "card_id")
	@JsonBackReference
	private CardMaster cardMaster;
	
	@Column(name = "card_token")
	private String cardToken;
	
	@Column(name = "card_unique_id")
	private String uniqueCardId;
	
	@Column(name = "cckey_part1")
	private String cckeyPart1;
	
	@Column(name = "cckey_status")
	private String cckeyStaus;
	
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
	
	public CCKeyMaster() {
		super();
	}

	public Long getCckeyId() {
		return cckeyId;
	}

	public void setCckeyId(Long cckeyId) {
		this.cckeyId = cckeyId;
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

	public String getCckeyPart1() {
		return cckeyPart1;
	}

	public void setCckeyPart1(String cckeyPart1) {
		this.cckeyPart1 = cckeyPart1;
	}

	public String getCckeyStaus() {
		return cckeyStaus;
	}

	public void setCckeyStaus(String cckeyStaus) {
		this.cckeyStaus = cckeyStaus;
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

	public CardMaster getCardMaster() {
		return cardMaster;
	}

	public void setCardMaster(CardMaster cardMaster) {
		this.cardMaster = cardMaster;
	}
	
}
