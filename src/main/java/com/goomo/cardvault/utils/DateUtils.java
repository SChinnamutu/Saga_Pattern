package com.goomo.cardvault.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class used to create different formats for date.
 * @author Manjunath Jakkandi
 */

public class DateUtils {
	
	private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    /**
     * @param dateTimeStr
     * @return
     */
    public static String getDateMonthWithTime(String dateTimeStr) {
        String formattedStr = dateTimeStr;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat days = new SimpleDateFormat("dd", Locale.ENGLISH);
            SimpleDateFormat months = new SimpleDateFormat("MMM");
            Date d = sdf.parse(dateTimeStr);
            String day = days.format(d);
            String month = months.format(d);
            formattedStr = day + "" + getDateSubScript(Integer.valueOf(day)) + " " + month;
        } catch (ParseException e) {
        	log.error("ParseException in getDateMonthWithTime " ,e);  
        } catch (Exception ex) {
        	log.error("Exception in getDateMonthWithTime " ,ex);  
        }
        return formattedStr;
    }
    
    
    public static Date getDate(String dateStr) {
    		Date date = null;
	    	try {
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    		date = sdf.parse(dateStr);
	    	}catch(Exception ex) {
	    		log.error("Exception in getDate " ,ex);  
	    	}
    		return date;
    }

    public static String getDateSubScript(int date) {
        String subScript = null;
        try {
            if (date == 1 || date == 21 || date == 31) {
                subScript = "st";
            } else if (date == 2 || date == 22) {
                subScript = "nd";
            } else if (date == 3 || date == 23) {
                subScript = "rd";
            } else if ((date > 3 && date <= 20) || (date > 23 && date <= 30)) {
                subScript = "th";
            }
        } catch (Exception e) {
        	log.error("Exception in getDateSubScript " ,e);
        }
        return subScript;
    }

    public static String getBookingConfirmDate(String dateTimeStr) {
        String formattedStr = dateTimeStr;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMMM-dd", Locale.getDefault());
            SimpleDateFormat days = new SimpleDateFormat("dd", Locale.ENGLISH);
            SimpleDateFormat months = new SimpleDateFormat("MMMMM yyyy");
            Date d = sdf.parse(dateTimeStr);
            String day = days.format(d);
            String month = months.format(d);
            formattedStr = day + "" + getDateSubScript(Integer.valueOf(day)) + " " + month;
        } catch (ParseException e) {
        	log.error("Exception in getBookingConfirmDate " ,e);
        } catch (Exception ex) {
            ex.getMessage();
        }
        return formattedStr;
    }

    public static String sessionScheduledDate(String dateTimeStr) {
        String formattedStr = dateTimeStr;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            SimpleDateFormat days = new SimpleDateFormat("dd", Locale.ENGLISH);
            SimpleDateFormat months = new SimpleDateFormat("MMM yyyy");
            Date d = sdf.parse(dateTimeStr);
            String day = days.format(d);
            String month = months.format(d);
            formattedStr = day + "" + getDateSubScript(Integer.valueOf(day)) + " " + month;
        } catch (ParseException e) {
        	log.error("ParseException in sessionScheduledDate " ,e);  
        } catch (Exception ex) {
        	log.error("Exception in sessionScheduledDate " ,ex); 
        }
        return formattedStr;
    }
    
    public static String convertDateToString(Date dateTime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    	String dateStr = sdf.format(dateTime);
    	return dateStr;
    }
    
    public static Date convertStringToDate(String dateStr) {
    	Date date = null;
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			date= sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date;
    }
    
	public static boolean compareTwoDates(String fromDate, String toDate, String format) {
		boolean isValid = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		simpleDateFormat.setLenient(false);
		Date fromDateValue;
		Date toDateValue;
		try {
			fromDateValue = simpleDateFormat.parse(fromDate);
			toDateValue = simpleDateFormat.parse(toDate);
			isValid = toDateValue.before(fromDateValue);
		} catch (ParseException e) {
			e.printStackTrace();
			return true;
		}
		return isValid;
	}
}
