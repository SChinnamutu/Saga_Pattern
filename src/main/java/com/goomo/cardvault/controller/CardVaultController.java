package com.goomo.cardvault.controller;

import javax.servlet.http.HttpServletRequest;

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

/**
 * This controller is used to define all the required API's for card vault.
 * @author Manjunath Jakkandi
 *
 */
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
	 * @author Manjunath Jakkandi
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
	
	/**
	 * This API is used to get meta data of card details.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = NamespaceConstants.CARD_DETAILS, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> getCardDetails(@RequestBody CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: Get Card Details :: Init");
		CardDetailsResponse response = cardVaultService.getCardDetails(request);
		log.info("Card Vault :: Get Card Details :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	
	/**
	 * This API is used to get encrypted card details. Without the keys, it's useless.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiIgnore
	@RequestMapping(value = NamespaceConstants.ENC_CARD_DETAILS, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> getEncryptedCardDetails(@RequestBody CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: Get Enc Card Details :: Init");
		CardDetailsResponse response = cardVaultService.getEncCardDetails(request);
		log.info("Card Vault :: Get Enc Card Details :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * This API is used to delete the card for specific card token.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @param httpServletRequest
	 * @return success for successful deletion of card details. Else, an error code along with a respective message.
	 * @throws Exception
	 */
	@RequestMapping(value = NamespaceConstants.DELETE_CARD, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> deleteCard(@RequestBody CardDetailsRequest request, HttpServletRequest httpServletRequest) throws Exception {
		log.info("Card Vault :: Delete Card :: Init");
		String authorization = httpServletRequest.getHeader("Authorization");
		CardDetailsResponse response = cardVaultService.deleteCard(request, authorization);
		log.info("Card Vault :: Delete Card :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * This class is used to store new card details.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = NamespaceConstants.STORE_NEW_CARD, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> storeCard(@RequestBody CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: Store Card :: Init");
		CardDetailsResponse response = cardVaultService.storeNewCard(request);
		log.info("Card Vault :: Store Card :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * This class is used to get CCKey1 stored in database.
	 * @param Manjunath Jakkandi
	 * @return
	 * @throws Exception
	 */
	@ApiIgnore
	@RequestMapping(value = NamespaceConstants.CCKEY_PART1, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> getCCKey1(@RequestBody CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: getCCKey1 :: Init");
		CardDetailsResponse response = cardVaultService.getECCKey1(request);
		log.info("Card Vault :: getCCKey1 :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * This API is used to retrieve card details for specific card belongs to specific user.
	 * @author Manjunath Jakkandi
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiIgnore
	@RequestMapping(value = NamespaceConstants.RETRIEVE_CARD_DATAILS, method = RequestMethod.POST)
	public ResponseEntity<CardDetailsResponse> retrieveCardDetails(@RequestBody CardDetailsRequest request) throws Exception {
		log.info("Card Vault :: retrieveCardDetails :: Init");
		CardDetailsResponse response = cardVaultService.retrieveCardDetails(request);
		log.info("Card Vault :: retrieveCardDetails :: Ends");
		return new ResponseEntity<CardDetailsResponse>(response, HttpStatus.OK);
	}
	
	
}
