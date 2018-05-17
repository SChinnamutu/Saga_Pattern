package com.goomo.cardvault.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goomo.cardvault.config.CardVaultDataSourceConfig;
import com.goomo.cardvault.constants.MessageCodes;
import com.goomo.cardvault.dao.CardVaultDAO;
import com.goomo.cardvault.dto.CardDTO;
import com.goomo.cardvault.dto.CardDetailsRequest;
import com.goomo.cardvault.dto.CardDetailsResponse;
import com.goomo.cardvault.dto.StatusMessage;
import com.goomo.cardvault.model.CCKeyMaster;
import com.goomo.cardvault.model.CardMaster;
import com.goomo.cardvault.utils.CommonUtils;
import com.goomo.cardvault.utils.CryptoUtils;
import com.goomo.cardvault.utils.DateUtils;

/**
 * This class used to create business logic for card vault service features/functions.
 * @author Manjunath Jakkandi
 *
 */
@Service
public class CardVaultService {
	
	private static final Logger log = LoggerFactory.getLogger(CardVaultService.class);
	
	@Autowired
	private CardVaultDAO cardVaultDAO;
	
	@Autowired
	private CardVaultDataSourceConfig dataSourceConfig;
	
	/**
	 * This method used to get the card details from user id or token or card unique id. 
	 * If none of them matches, returns error response.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CardDetailsResponse getCardDetails(CardDetailsRequest request) throws Exception{
		log.info("Card Vault :: Get Card Details :: request :: "+request.toString());
		CardDetailsResponse response = new CardDetailsResponse();
		if(request.getUserId()!=null && !request.getUserId().isEmpty()) {
			log.info("Card Vault :: Get Card Details :: By userId :: "+request.getUserId());
			List<CardMaster> cardMasterList = cardVaultDAO.fetchCardDetailsByUserId(request.getUserId());
			if(cardMasterList!=null && cardMasterList.size() > 0) {
				CardDTO card = null;
				List<CardDTO> cardList = new ArrayList<CardDTO>();
				for(CardMaster cardMaster : cardMasterList) {
					 card = processCardDetails(cardMaster);
					 if(card!=null) {
						 cardList.add(card);
					 }
				}
				response.setStatus(MessageCodes.SUCCESS);
				response.setStatusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG, MessageCodes.SUCCESS_DESC));
				response.setCardList(cardList);
			}else {
				createSearchErrorResponse(response);
			}
		}else if(request.getCardToken()!=null && !request.getCardToken().isEmpty()) {
			log.info("Card Vault :: Get Card Details :: By token :: "+request.getCardToken());
			CardMaster cardMaster = cardVaultDAO.fetchCardDetailsByToken(request.getCardToken());
			CardDTO card = processCardDetails(cardMaster);
			if(card!=null) {
				response.setStatus(MessageCodes.SUCCESS);
				response.setStatusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG, MessageCodes.SUCCESS_DESC));
				response.setCard(card);
			}else {
				createSearchErrorResponse(response);
			}
		}else if(request.getCardUniqueId()!=null && !request.getCardUniqueId().isEmpty()) {
			log.info("Card Vault :: Get Card Details :: By unque card id :: "+request.getCardUniqueId());
			CardMaster cardMaster = cardVaultDAO.fetchCardDetailsByUniqueId(request.getCardUniqueId());
			CardDTO card = processCardDetails(cardMaster);
			if(card!=null) {
				response.setStatus(MessageCodes.SUCCESS);
				response.setStatusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG, MessageCodes.SUCCESS_DESC));
				response.setCard(card);
			}else {
				createSearchErrorResponse(response);
			}
		}else {
			response.setStatus(MessageCodes.BAD_REQUEST);
			response.setStatusMessage(new StatusMessage(MessageCodes.BAD_REQUEST_MSG, MessageCodes.BAD_REQUEST_DESC));
		}
		log.info("Card Vault :: Get Card Details :: response :: "+response.toString());
		return response;
	}
	
	
	/**
	 * This method used to transform object from card master to card dto for only required parameters.
	 * @author Manjunath Jakkandi
	 * @param cardMaster
	 * @return
	 */
	public CardDTO processCardDetails(CardMaster cardMaster) {
		CardDTO card = null;
		if(cardMaster!=null) {
			card = new CardDTO();
			card.setUserId(cardMaster.getUserId());
			card.setUniqueCardId(cardMaster.getUniqueCardId());
			card.setCardToken(cardMaster.getCardToken());
			card.setCardStatus(cardMaster.getCardStatus());
			card.setMaskedCardNumber(cardMaster.getMaskedCardNumber());
			card.setCardIssuedBy(cardMaster.getCardIssuedBy());
			card.setCardType(cardMaster.getCardType());
		}
		return card;
	}
	
	
	/**
	 * This method used to create error response for get card details when no records found.
	 * @author Manjunath Jakkandi
	 * @param response
	 * @return
	 */
	public CardDetailsResponse createSearchErrorResponse(CardDetailsResponse response) {
		if(response == null) {
			response = new CardDetailsResponse();
		}
		response.setStatus(MessageCodes.INVALID_RESPONSE);
		response.setStatusMessage(new StatusMessage(MessageCodes.NO_RECORDS_MSG, MessageCodes.NO_RECORDS_DESC));
		return response;
	}
	
	
	public CardDetailsResponse storeNewCard(CardDetailsRequest request) throws Exception{
		log.info("Card Vault :: Store Card :: request :: "+request.toString());
		CardDetailsResponse response = new CardDetailsResponse();
		if(request.getEncCardDetails()!=null && !request.getEncCardDetails().isEmpty()) {
			
			// decrypt pre-encrypted card details
			String preDecryptCardDetails = CryptoUtils.decrypt(request.getEncCardDetails(), dataSourceConfig.getDataSource().getAesSecretKey());
			String[] preDecryptCardDetailsArr =  preDecryptCardDetails.split(Pattern.quote("^^"));
			String cardNumber = preDecryptCardDetailsArr[0];
			String nameOnCard = preDecryptCardDetailsArr[1];
			String cardExpryDate = preDecryptCardDetailsArr[2];
			
			// generate random key :: CCKey
			String ccKey = CommonUtils.generate16DigitCardId();
			log.info("Card Vault :: Store Card :: ccKey :: "+ccKey);
			
			// encrypt card details with CCKey
			String encCardDetails = createEncCardDetails(cardNumber, nameOnCard, cardExpryDate, ccKey);
			log.info("Card Vault :: Store Card :: encCardDetails :: "+encCardDetails);
			
			// encrypt cckey with cmk
			String encCCKey = CryptoUtils.encrypt(ccKey, request.getCmk());
			log.info("Card Vault :: Store Card :: encCCKey :: "+encCCKey);
			
			// split the encCCKey into two parts.
			String part1 = encCCKey.substring(0, (encCCKey.length()/2));
			String part2 = encCCKey.substring((encCCKey.length()/2));
			log.info("Card Vault :: Store Card :: part1 :: "+part1);
			log.info("Card Vault :: Store Card :: part2 :: "+part2);
			
			// store card details in database
			CardMaster cardMaster = createCardMaster(request, cardNumber, nameOnCard, cardExpryDate, part1);
			CardMaster storedCardMaster = cardVaultDAO.storeCard(cardMaster);
			if(cardMaster!=null && cardMaster.getCardId() > 0) {
				log.info("Card Vault :: Store Card :: CardId :: "+cardMaster.getCardId());
				response.setStatus(MessageCodes.SUCCESS);
				response.setStatusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG, MessageCodes.SUCCESS_DESC));
				response.setCckeyPart2(part2);
				response.setToken(storedCardMaster.getCardToken());
				response.setUserId(storedCardMaster.getUserId());
				response.setCardUniqueId(storedCardMaster.getUniqueCardId());
				response.setCardType(storedCardMaster.getCardType());
				response.setCardIssuedBy(storedCardMaster.getCardIssuedBy());
				response.setTokenExpiry(storedCardMaster.getCreatedAt());
			}else {
				throw new Exception();
			}
		}else {
			throw new IllegalArgumentException(MessageCodes.UN_AUTHORISATION);
		}
		log.info("Card Vault :: Store Card :: response :: "+response.toString());
		return response;
	}
	
	
	private CardMaster createCardMaster(CardDetailsRequest request, String cardNumber, String nameOnCard, 
			String cardExpiryDate, String ccKeyPart1) throws Exception{
		
		// card master object
		CardMaster cardMaster = new CardMaster();
		String cardToken = CommonUtils.generate16DigitCardId();
		cardMaster.setCardToken(cardToken);
		String cardUniqueId = CommonUtils.generate36DigitUniqueId();
		cardMaster.setUniqueCardId(cardUniqueId);
		cardMaster.setEncryptedCardDetails(request.getEncCardDetails());
		cardMaster.setMaskedCardNumber(CommonUtils.maskContent(cardNumber, false, 0, 4, "X"));
		cardMaster.setCardType("CREDIT");
		cardMaster.setCardIssuedBy("MASTREO");
		cardMaster.setCardStatus("A");
		cardMaster.setUserId(request.getUserId());
		cardMaster.setCreatedBy(request.getTxnBy());
		Date currentDateTime = new Date();
		String createdDateTimeStr = DateUtils.convertDateToString(currentDateTime);
		cardMaster.setCreatedAt(createdDateTimeStr);
		
		// cckey master object
		CCKeyMaster ccKeyMaster = new CCKeyMaster();
		ccKeyMaster.setCardMaster(cardMaster);
		ccKeyMaster.setCardToken(cardToken);
		ccKeyMaster.setUniqueCardId(cardUniqueId);
		ccKeyMaster.setCckeyPart1(ccKeyPart1);
		ccKeyMaster.setCckeyStaus("A");
		ccKeyMaster.setUserId(request.getUserId());
		ccKeyMaster.setCreatedBy(request.getTxnBy());
		ccKeyMaster.setCreatedAt(createdDateTimeStr);
		
		// set cckey master object to card master
		List<CCKeyMaster> ccKeyMasterList = new ArrayList<CCKeyMaster>();
		ccKeyMasterList.add(ccKeyMaster);
		cardMaster.setCckeyMasterList(ccKeyMasterList);
		
		return cardMaster;
	}
	
	private String createEncCardDetails(String cardNumber, String nameOnCard, String expiryDate, String ccKey) throws Exception{
		String encCardDetails = null;
		String cardDetailsFormat= cardNumber +"|"+nameOnCard + "|" + expiryDate;
		encCardDetails = CryptoUtils.encrypt(cardDetailsFormat, ccKey);
		return encCardDetails;
	}
	
	private String decryptEncCardDetails(String encCardDetails, String ccKey) throws Exception{
		String	cardDetails = CryptoUtils.decrypt(encCardDetails, ccKey);
		return cardDetails;
	}
	
}
