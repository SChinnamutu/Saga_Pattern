package com.goomo.cardvault.constants;

/**
 * 
 * MessageCodes class for message codes for card vault service
 * @author Manjunath Jakkandi
 *
 */

public class MessageCodes {

	public static final String SUCCESS = "200";
	public static final String SUCCESS_MSG = "SUCCESS";
	public static final String SUCCESS_DESC = "Request processed successfully";

	public static final String NOT_FOUND = "404";

	public static final String ERROR = "ERROR";

	public static final String NON_AUTHORATIVE_INFORMATION = "203";
	public static final String NON_AUTHORATIVE_INFORMATION_MSG = "Non Authorative Response";
	public static final String NON_AUTHORATIVE_INFORMATION_DESC = "Non Authorative Response Error. Please try again later";

	public static final String INTERNAL_SERVER_ERROR = "500";
	public static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error";
	public static final String INTERNAL_SERVER_ERROR_DESC = "Internal Server Error. Please try again later.";

	public static final String INVALID_RESPONSE = "502";
	public static final String INVALID_RESPONSE_MSG = "INVALID_RESPONSE";
	public static final String INVALID_RESPONSE_DESC = "Invalid Response. Please try again later";

	public static final String BAD_REQUEST = "400";
	public static final String BAD_REQUEST_MSG = "BAD_REQUEST";
	public static final String BAD_REQUEST_DESC = "Invalid Request. Please provide valid request.";

	public static final String REQUEST_TIMEOUT = "408";
	public static final String REQUEST_TIMEOUT_MSG = "REQUEST_TIMEOUT";
	public static final String REQUEST_TIMEOUT_DESC = "Request Time Out. Please try again later.";

	public static final String GATEWAY_TIMEOUT = "504";
	public static final String GATEWAY_TIMEOUT_MSG = "GATEWAY_TIMEOUT";
	public static final String GATEWAY_TIMEOUT_DESC = "Gateway Time Out. Please try again later.";

	public static final String UN_AUTHORISATION = "401";
	public static final String UN_AUTHORISATION_MSG = "UN_AUTHORISATION";
	public static final String UN_AUTHORISATION_DESC = "Unauthrised data. Please provide valid authorization request.";
	
	public static final String UNSUPPORTED_MEDIA_TYPE = "415";
	public static final String UNSUPPORTED_MEDIA_TYPE_MSG = "INVALID_JSON_FORMAT";
	public static final String UNSUPPORTED_MEDIA_TYPE_DESC = "Unsupported Media Type. Please provide valid JSON format.";

	
	public static final String CARD_NUMBER_NUMARIC = "Card Number should be numaric";
	public static final String INVALID_CARD_NUMBER = "Invalid Card Number";
	public static final String INVLAID_CARD_EXPIRY_MONTH = "Card expiry month should be numaric and between 1 and 12";
	public static final String INVALID_CARD_EXPIRY_YEAR = "Card expiry year should be numaric and between 00 and 99";
	public static final String CARD_EXPIRED = "Card Expired";
	public static final String INVALID_CARD_HOLDER_NAME = "Card Holder Name required";
	public static final String INVALID_CARD_CVV = "Invalid CVV";
	
	public static final String HEALTH_CHECK_RES_DESC = "Health check successful";
	public static final String NO_RECORDS_DESC = "Sorry, No records found";
	public static final String NO_RECORDS_MSG = "NO_RECORDS";
	
	
	
}
