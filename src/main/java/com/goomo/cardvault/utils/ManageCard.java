package com.goomo.cardvault.utils;

import java.util.regex.Pattern;

public class ManageCard {
	
	public static void initManageCard() throws Exception{
		/**
		 * Card Storage Process
		 */
		System.out.println("********************* Init Card Storage Process *********************");
		String cardNumber = "4838340598010569";
		String nameOnCard = "Manjunath Jakkandi";
		String cardExpryDate = "04/22";
		System.out.println("cardNumber :: "+cardNumber);
		System.out.println("nameOnCard :: "+nameOnCard);
		System.out.println("cardExpryDate :: "+cardExpryDate);
		
		// validate if card is expired
		boolean isCardExpiredFlag = DateUtils.isCardExpired(cardExpryDate);
		System.out.println("isCardExpiredFlag >> "+isCardExpiredFlag);
		
		// pre encrypted card details
		String cardDetails = cardNumber + "^^" + nameOnCard + "^^" + cardExpryDate;
		String preEncCardDetails = CryptoUtils.encrypt(cardDetails, "0102030405060708");
		System.out.println("preEncCardDetails :: "+preEncCardDetails);
		String preDecryptCardDetails = CryptoUtils.decrypt(preEncCardDetails, "0102030405060708");
		String[] preDecryptCardDetailsArr =  preDecryptCardDetails.split(Pattern.quote("^^"));
		cardNumber = preDecryptCardDetailsArr[0];
		nameOnCard = preDecryptCardDetailsArr[1];
		cardExpryDate = preDecryptCardDetailsArr[2];
		System.out.println("preDecryptCardDetails :: "+preDecryptCardDetails);
		
		
		// generate random key :: CCKey
		String ccKey = CommonUtils.generate16DigitCardId();
		System.out.println("ccKey :: "+ccKey);
		
		// generate masked card number
		String maskedCardNumber = CommonUtils.maskContent(cardNumber, false, 6, 4, "X");
		System.out.println("maskedCardNumber :: "+maskedCardNumber);
		
		// encrypt card details with CCKey
		String encCardDetails = createEncCardDetails(cardNumber, nameOnCard, cardExpryDate, ccKey);
		System.out.println("encCardDetails :: "+encCardDetails);
		System.out.println("********************* Ends Card Storage Process *********************\n\n");
		
		/**
		 * Protection of cckey
		 */
		System.out.println("********************* Init CCKey Protection Process *********************");
		// get cmk of user
		String cmk = CommonUtils.generate16DigitCardId();
		System.out.println("CMK of user :: "+cmk);
		
		// encrypt cckey with cmk
		String encCCKey = CryptoUtils.encrypt(ccKey, cmk);
		System.out.println("encCCKey :: "+encCCKey);
		//String decCCKey1 = CryptoUtils.decrypt(encCCKey, cmk);
		//System.out.println("decCCKey :: "+decCCKey1);
		
		// split the encCCKey into two parts.
		String part1 = encCCKey.substring(0, (encCCKey.length()/2));
		String part2 = encCCKey.substring((encCCKey.length()/2));
		System.out.println("part1 :: "+part1);
		System.out.println("part2 :: "+part2);
		System.out.println("********************* Ends CCKey Protection Process *********************\n\n");
		
		
		
		/**
		 * Card Retrieval process
		 */
		System.out.println("********************* Init Card Retrieval Process *********************");
		// assemple part1 and part2
		String encCCKeyAssembled = part1.concat(part2);
		System.out.println("encCCKey :: "+encCCKey);
		
		// decrypt encCCKey from cmk :: cckey
		String decCCKey = CryptoUtils.decrypt(encCCKeyAssembled, cmk);
		System.out.println("ccKey :: "+decCCKey);
		
		// decrypt card
		String decCardDetails = decryptEncCardDetails(encCardDetails, decCCKey);
		if(decCardDetails!=null && decCardDetails.isEmpty()) {
			System.out.println("decCardDetails :: "+decCardDetails);
			
			// split the decrypted card details
			String cardDetailsArr[] = decCardDetails.split(Pattern.quote("|"));
			System.out.println("cardNumber :: "+ cardDetailsArr[0]);
			System.out.println("nameOnCard :: "+ cardDetailsArr[1]);
			System.out.println("cardExpryDate :: "+ cardDetailsArr[2]);
		}else {
			System.out.println("Internal Server Error");
		}
		
		System.out.println("********************* Ends Card Retrieval Process *********************\n\n");
	}
	
	private static String createEncCardDetails(String cardNumber, String nameOnCard, String expiryDate, String ccKey) {
		String encCardDetails = null;
		try {
			String cardDetailsFormat= cardNumber +"|"+nameOnCard + "|" + expiryDate;
			encCardDetails = CryptoUtils.encrypt(cardDetailsFormat, ccKey);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return encCardDetails;
	}
	
	private static String decryptEncCardDetails(String encCardDetails, String ccKey) {
		String cardDetails = null;
		try {
			cardDetails = CryptoUtils.decrypt(encCardDetails, ccKey);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return cardDetails;
	}
	
	
	public static void main(String args[]) throws Exception{
		initManageCard();
	}
	
}
