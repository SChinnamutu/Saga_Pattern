package com.goomo.cardvault.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goomo.cardvault.auth.JWTAuth;
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
	 * This method is used to decrypt user id or cust id if it is encrypted with jwt token.
	 * @author Manjunath Jakkandi
	 * @param authorization
	 * @return
	 * @throws Exception
	 */
	public String decryptUserIdByJWT(String authorization) throws Exception{
		String custId = null;
		if(authorization!=null && !authorization.isEmpty()) {
			authorization = authorization.replace("Bearer ", "");
			log.info("Card Vault :: Verify UserId ::  authorization :: "+authorization);
			custId = JWTAuth.decryptToken(authorization, dataSourceConfig.getDataSource().getJwtSecretKey());
		}
		return custId;
	}
	
	/**
	 * This method used to delete the card details. Input parameters is either card token or card unique id. If found, card
	 * details are fetched and deleted.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CardDetailsResponse deleteCard(CardDetailsRequest request, String authorization) throws Exception {
		log.info("Card Vault :: Delete Card :: request :: "+request.toString());
		CardDetailsResponse response = new CardDetailsResponse();
		
		// verify user id or cust id from jwt token. if exists, decrypt through jwt.
		if(authorization!=null && !authorization.isEmpty()) {
			String userId = decryptUserIdByJWT(authorization);
			if(userId!=null && !userId.isEmpty()) {
				request.setUserId(userId);
			}else {
				throw new IllegalArgumentException(MessageCodes.UN_AUTHORISATION);
			}
		}
		
		// validate and process delete card if card token or card unique id is valid
		if(request.getCardToken()!=null && !request.getCardToken().isEmpty() && request.getUserId()!=null && !request.getUserId().isEmpty()) {
			log.info("Card Vault :: Delete Card :: By token :: "+request.getCardToken());
			CardMaster cardMaster = cardVaultDAO.fetchCardDetailsByTokenAndUserId(request.getCardToken(), request.getUserId());
			processDeleteCard(cardMaster, response);
		}else if(request.getCardUniqueId()!=null && !request.getCardUniqueId().isEmpty()) {
			log.info("Card Vault :: Delete Card :: By unque card id :: "+request.getCardUniqueId());
			CardMaster cardMaster = cardVaultDAO.fetchCardDetailsByUniqueIdAndUserId(request.getCardUniqueId(), request.getUserId());
			processDeleteCard(cardMaster, response);
		}else {
			response.setStatus(MessageCodes.BAD_REQUEST);
			response.setStatusMessage(new StatusMessage(MessageCodes.BAD_REQUEST_MSG, MessageCodes.BAD_REQUEST_DESC));
		}
		log.info("Card Vault :: Delete Card :: response :: "+response.toString());
		return response;
	}
	
	public void processDeleteCard(CardMaster cardMaster, CardDetailsResponse response) throws Exception {
		if(cardMaster!=null) {
			cardVaultDAO.deleteCard(cardMaster);
			response.setStatus(MessageCodes.SUCCESS);
			response.setStatusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG, MessageCodes.SUCCESS_DESC));
		}else {
			createSearchErrorResponse(response);
		}
	}
	
	/**
	 * This method used to get card details along with encrypted card details as well to process the details further in
	 * payment engine service. This method should not be exposed to clients.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public CardDetailsResponse getEncCardDetails(CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: Get Enc Card Details :: request :: "+request.toString());
		CardDetailsResponse response = new CardDetailsResponse();
		if(request.getCardToken()!=null && !request.getCardToken().isEmpty()) {
			log.info("Card Vault :: Get Enc Card Details :: By token :: "+request.getCardToken());
			CardMaster cardMaster = cardVaultDAO.fetchCardDetailsByToken(request.getCardToken());
			CardDTO card = processCardDetails(cardMaster, true);
			if(card!=null) {
				response.setStatus(MessageCodes.SUCCESS);
				response.setStatusMessage(new StatusMessage(MessageCodes.SUCCESS_MSG, MessageCodes.SUCCESS_DESC));
				response.setCard(card);
			}else {
				createSearchErrorResponse(response);
			}
		}else if(request.getCardUniqueId()!=null && !request.getCardUniqueId().isEmpty()) {
			log.info("Card Vault :: Get Enc Card Details :: By unque card id :: "+request.getCardUniqueId());
			CardMaster cardMaster = cardVaultDAO.fetchCardDetailsByUniqueId(request.getCardUniqueId());
			CardDTO card = processCardDetails(cardMaster, true);
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
		log.info("Card Vault :: Get Enc Card Details :: response :: "+response.toString());
		return response;
	}
	
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
					 card = processCardDetails(cardMaster, false);
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
			CardDTO card = processCardDetails(cardMaster, false);
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
			CardDTO card = processCardDetails(cardMaster, false);
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
	public CardDTO processCardDetails(CardMaster cardMaster, boolean isAddEncCardDetails) {
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
			card.setCardExpired(DateUtils.isCardExpired(cardMaster.getCardExpiryDate(), new Date()));
			if(isAddEncCardDetails) {
				card.setEncCardDetails(cardMaster.getEncryptedCardDetails());
			}
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
	
	/**
	 * This method used to create error response for delete card details when no card details not found.
	 * @author Manjunath Jakkandi
	 * @param response
	 * @return
	 */
	public CardDetailsResponse createDeleteErrorResponse(CardDetailsResponse response) {
		if(response == null) {
			response = new CardDetailsResponse();
		}
		response.setStatus(MessageCodes.INVALID_RESPONSE);
		response.setStatusMessage(new StatusMessage(MessageCodes.INVALID_RESPONSE_MSG, MessageCodes.INVALID_RESPONSE_DESC));
		return response;
	}
	
	
	public CardDetailsResponse storeNewCard(CardDetailsRequest request) throws Exception{
		log.info("Card Vault :: Store Card :: request :: "+request.toString());
		
		// validate card details
		if(request.getEncCardDetails()==null || request.getEncCardDetails().isEmpty()) {
			throw new IllegalArgumentException(MessageCodes.UN_AUTHORISATION);
		}
		String preDecryptCardDetails = CryptoUtils.decrypt(request.getEncCardDetails(), dataSourceConfig.getDataSource().getAesSecretKey());
		CardDTO dto = CommonUtils.getCardDetails(preDecryptCardDetails);
		CommonUtils.validateCardDetails(dto.getCardNumber(), dto.getCardHolderName(), dto.getCardExpiryMonthYear());
		
		// generate random key :: CCKey
		String ccKey = CommonUtils.generate16DigitCardId();
		log.info("Card Vault :: Store Card :: ccKey :: "+ccKey);
		
		// encrypt card details with CCKey
		String encCardDetails = createEncCardDetails(dto.getCardNumber(), dto.getCardHolderName(), dto.getCardExpiryMonthYear(), ccKey);
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
		CardMaster cardMaster = createCardMaster(request, dto.getCardNumber(), dto.getCardHolderName(), dto.getCardExpiryMonthYear(), part1);
		CardMaster storedCardMaster = cardVaultDAO.storeCard(cardMaster);
		CardDetailsResponse response = new CardDetailsResponse();
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
			response.setStatus(MessageCodes.INVALID_RESPONSE);
			response.setStatusMessage(new StatusMessage(MessageCodes.INVALID_RESPONSE_MSG, MessageCodes.INVALID_RESPONSE_DESC));
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
		cardMaster.setMaskedCardNumber(CommonUtils.maskContent(cardNumber, false, 6, 4, "X"));
		cardMaster.setCardType("CREDIT");
		cardMaster.setCardIssuedBy("MASTREO");
		cardMaster.setCardStatus("A");
		cardMaster.setUserId(request.getUserId());
		cardMaster.setCreatedBy(request.getTxnBy());
		cardMaster.setCardExpiryDate(DateUtils.getCardExpiryDateFormat(cardExpiryDate));
		
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
