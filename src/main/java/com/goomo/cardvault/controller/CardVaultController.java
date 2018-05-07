package com.goomo.cardvault.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goomo.cardvault.constants.MessageCodes;
import com.goomo.cardvault.constants.NamespaceConstants;
import com.goomo.cardvault.dto.BaseResponse;
import com.goomo.cardvault.dto.CardDetailsRequest;
import com.goomo.cardvault.dto.CardDetailsResponse;
import com.goomo.cardvault.dto.StatusMessage;
import com.goomo.cardvault.service.CardVaultService;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = NamespaceConstants.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "CardVault Controller", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardVaultController {

	private static final Logger log = LoggerFactory.getLogger(CardVaultController.class);
	private static BaseResponse response = new BaseResponse();
	private static StatusMessage statusMessage = new StatusMessage();
	
	@Autowired
	CardVaultService cardVaultService;
	
	/**
	 * This method used to check the card vault application status
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = NamespaceConstants.HEALTH_CHECK, method = RequestMethod.GET)
	@ApiIgnore
	public ResponseEntity<BaseResponse> healthCheck() throws Exception {
		if (response == null) {
			response = new BaseResponse();
		}
		if (statusMessage == null) {
			statusMessage = new StatusMessage();
		}
		response.setStatus(MessageCodes.SUCCESS);
		statusMessage.setCode(MessageCodes.SUCCESS_MSG);
		statusMessage.setDescription(MessageCodes.HEALTH_CHECK_RES_DESC);
		response.setStatusMessage(statusMessage);
		return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = NamespaceConstants.CARD_DETAILS, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> getCardDetails(@RequestBody CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: Get Card Details :: Init");
		CardDetailsResponse response = cardVaultService.getCardDetails(request);
		log.info("Card Vault :: Get Card Details :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = NamespaceConstants.STORE_NEW_CARD, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> storeCard(@RequestBody CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: Store Card :: Init");
		CardDetailsResponse response = cardVaultService.storeNewCard(request);
		log.info("Card Vault :: Store Card :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	
	
	
}
