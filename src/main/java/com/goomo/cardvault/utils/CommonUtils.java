package com.goomo.cardvault.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.goomo.cardvault.constants.MessageCodes;
import com.goomo.cardvault.dto.CardDTO;


/**
 * This class is used for implemeting the all common util operations
 * 
 * @author Manjunath Jakkandi
 *
 */

public class CommonUtils {

	public static String generateEightDigitUniqueId() {
		java.util.Random generator = new java.util.Random();
		generator.setSeed(System.currentTimeMillis());
		int i = generator.nextInt(10000000) % 10000000;
		java.text.DecimalFormat f = new java.text.DecimalFormat("00000000");
		return f.format(i);
	}
	
	public static String generate16DigitCardId() {
		String str1 = generateEightDigitUniqueId();
		String str2 = generateEightDigitUniqueId();
		return str1.concat(str2);
	}
	
	public static String generate36DigitUniqueId() {
		return UUID.randomUUID().toString();
	}

	public static boolean isNumaric(String checkValue) {
		boolean isNumaric = false;
		if (checkValue.matches("-?\\d+(\\\\d+)?")) {
			isNumaric = true;
		}
		return isNumaric;
	}
	

	public static boolean checkIsNullOrEmpty(String data) {
		boolean emptyCheck = false;
		if (data == null || data.isEmpty()) {
			emptyCheck = true;
		}
		return emptyCheck;
	}



	public static boolean isAlphaNumaric(String data) {
		boolean isNumeric = data.matches("[A-Za-z0-9]+");
		return isNumeric;
	}
	
	public static String formatAmount(String amount) {
		double amountValue = Double.valueOf(amount);
		long val = (long) (amountValue * 100);
		String paisaAmount = String.valueOf(val);
		return paisaAmount;
	}
	
	/**
     * Masking the security data with index and specific char like X or *. For
     * Ex: If Pan=1111222233334444, StartIndex=2 and EndIndex=4 then
     * MaskValue=11**********4444.
     * 
     * @param value
     *            - Original Value
     * @param isAll
     *            - Mask the all chars or not
     * @param startIndex
     *            - Index of Start Char
     * @param endIndex
     *            - Index of End Char
     * @param maskChar
     *            - Mask char as like X or * or else
     * @return - Mask the data.
     */
    public static String maskContent(String content, boolean isAll, int startIndex, int endIndex, String maskChar) {
        String temp = "";
        String temp2 = "";
        String temp3 = "";
        if (isAll) {
        	content = getMasked(content.length(), maskChar);
        } else {
            if (startIndex < content.length()) {
                temp = content.substring(0, startIndex);
                temp2 = content.substring(startIndex, content.length() - endIndex);
                temp2 = getMasked(temp2.length(), maskChar);
                temp3 = content.substring(temp.length() + temp2.length(), temp.length() + temp2.length() + endIndex);
            }
            content = temp + temp2 + temp3;
        }
        return content;
    }

    public static String getMasked(int length, String symbol) {
        String temp = "";
        for (int i = 0; i < length; i++) {
            temp = symbol + temp;
        }
        return temp;
    }
    
	public static boolean isHtmlContent(String content) {

		boolean isHtmlContent = false;
		String tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
		String tagEnd = "\\</\\w+\\>";
		String tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
		String htmlEntity = "&[a-zA-Z][a-zA-Z0-9]+;";
		Pattern htmlPattern = Pattern.compile(
				"(" + tagStart + ".*" + tagEnd + ")|(" + tagSelfClosing + ")|(" + htmlEntity + ")", Pattern.DOTALL);
		if (htmlPattern.matcher(content).find()) {
			isHtmlContent = true;
		}
		return isHtmlContent;
	}
	public static boolean isValidCardNumber(String cardNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(cardNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return (sum % 10 == 0);
	}
	
	public static boolean isvalidCVV(String cvv, String cardNumber) {
		Pattern pattern = null;
		if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
			pattern = Pattern.compile("\\d{4}");
		} else {
			pattern = Pattern.compile("\\d{3}");
		}
		Matcher matcher = pattern.matcher(cvv);
		return matcher.matches();
	}

	public static boolean isvalidMonth(String monthStr) {
		int month = Integer.parseInt(monthStr);
		if (month >= 1 && month <= 12) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isvalidYear(String yearStr) {
		int year = Integer.parseInt(yearStr);
		if (year >= 00 && year <= 99) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isCardExpires(String cardexpiry) {
		boolean expired = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
		simpleDateFormat.setLenient(false);
		Date expiry;
		try {
			expiry = simpleDateFormat.parse(cardexpiry);
			expired = expiry.before(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		return expired;
	}
	
	public static Date StringToDateFormat(String dateString, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static String toCamelCase(final String init) {
		if (init == null) {
			return null;
		}
		final StringBuilder ret = new StringBuilder(init.length());
		for (final String word : init.split(" ")) {
			if (!word.isEmpty()) {
				ret.append(word.substring(0, 1).toUpperCase());
				ret.append(word.substring(1).toLowerCase());
			}
			if (!(ret.length() == init.length())) {
				ret.append(" ");
			}
		}
		return ret.toString();
	}
	
	public static CardDTO getCardDetails(String preDecryptCardDetails) throws Exception{
		String[] preDecryptCardDetailsArr =  preDecryptCardDetails.split(Pattern.quote("^^"));
		String cardNumber = preDecryptCardDetailsArr[0];
		String nameOnCard = preDecryptCardDetailsArr[1];
		String cardExpryDate = preDecryptCardDetailsArr[2];
		CardDTO dto = new CardDTO(cardNumber, cardExpryDate, nameOnCard);
		return dto;
	}
	
	public static void validateCardDetails(String cardNumber, String nameOnCard, String cardExpiryDate) throws Exception{
		if (CommonUtils.checkIsNullOrEmpty(cardNumber)
				|| !CommonUtils.isNumaric(cardNumber)) {
			throw new IllegalArgumentException(MessageCodes.CARD_NUMBER_NUMARIC);
		}
		if (!CommonUtils.isValidCardNumber(cardNumber)) {
			throw new IllegalArgumentException(MessageCodes.INVALID_CARD_NUMBER);
		}
		boolean isCardExpired = DateUtils.isCardExpired(cardExpiryDate);
		if(isCardExpired) {
			throw new IllegalArgumentException(MessageCodes.CARD_EXPIRY_MSG);
		}
		if (CommonUtils.checkIsNullOrEmpty(nameOnCard)) {
			throw new IllegalArgumentException(MessageCodes.INVALID_CARD_HOLDER_NAME);
		}
	}
}