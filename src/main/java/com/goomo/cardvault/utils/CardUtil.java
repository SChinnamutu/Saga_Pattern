package com.goomo.cardvault.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class used to support helper methods for card
 * @author Manjunath Jakkandi
 *
 */
public class CardUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CardUtil.class);
	/**
	 * Method to process card number to detect brand type
	 * @author Manjunath Jakkandi
	 * @param cardNumber
	 */
	public static String processCardBrand(String cardNumber) {
		String brandType = "INVALID";
		if(cardNumber!=null && !cardNumber.isEmpty()) {
			String cardBin =  cardNumber.substring(0,6);
			log.info("CardUtil :: processCardBrand :: Masked Card Number :: "+cardNumber);
			log.info("CardUtil :: processCardBrand :: Card Bin :: "+cardBin);
			brandType = getBrandType(cardNumber, cardBin);
			log.info("CardUtil :: processCardBrand :: Card Brand :: "+brandType);
		}
		return brandType;
	}
	
	/**
	 * Algorithm to detect card brand type based on matching pattern and card length
	 * @author Manjunath Jakkandi
	 * @param cardNumber
	 * @param cardBin
	 * @return
	 */
	public static String getBrandType(String cardNumber, String cardBin) {
		int cardBinInt;
		int cardLen = cardNumber.length();
		 
		// American Express
		if(cardBin.matches("(34|37).*") && cardLen == 15) {
			return "Amex";
		}
		
		// Diners Club - Carte Blanche
		if(cardBin.matches("(300|301|302|303|304|305).*") && cardLen == 14) {
			return "Citi Diners";
		}
		
		// Diners Club - International
		if(cardBin.matches("(36).*") && cardLen == 14) {
			return "Citi Diners";
		}
		
		// Discover
		cardBinInt = Integer.parseInt(cardBin);
		if(cardBin.matches("(6011|644|645|646|647|648|649|65).*") && (cardLen == 16 || cardLen == 19)) {
			return "Discover";
		}
		if(cardBinInt >=622126 && cardBinInt <=622925  && (cardLen == 16 || cardLen == 19)) {
			return "Discover";
		}
		
		// InstaPayment
		if(cardBin.matches("(637|638|639).*") && cardLen == 16) {
			return "InstaPayment";
		}
		
		// JCB
		cardBinInt = Integer.parseInt(cardBin.substring(0, 4));
		if(cardBinInt >=3528 && cardBinInt <= 3589 && (cardLen == 16 || cardLen == 19)) {
			return "JCB";
		}
		
		// Maestro
		if(cardBin.matches("(5018|5020|5038|5893|6304|6759|6761|6762|6763).*") && (cardLen == 16 || cardLen == 19)) {
			return "Maestro";
		}
		
		// MasterCard
		cardBinInt = Integer.parseInt(cardBin);
		if(cardBin.matches("(51|52|53|54|55).*") && cardLen == 16) {
			return "MasterCard";
		}
		if(cardBinInt >=222100 && cardBinInt <=272099 && cardLen == 16) {
			return "MasterCard";
		}
		
		// Visa
		if(cardBin.startsWith("4") && (cardLen == 13 || cardLen == 16 || cardLen == 19)) {
			return "Visa";
		}
		
		// if nothing matches
		return "INVALID";
	}
	
}
