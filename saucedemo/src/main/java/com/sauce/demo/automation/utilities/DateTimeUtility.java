package com.sauce.demo.automation.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.WebElement;


public class DateTimeUtility {

	/**
	 * Name: isLeapYearFromInteger
	 * @author Swatee Shrivastava
	 * @creation date : 8-Oct-2020
	 * @version 1.0
	 * Description: This function is used to calculate leap year for integer 
	 * @param year - This parameter holds year value
	 * @return true/false
	 * Usage: boolean leapYear = isLeapYearFromInteger(year);
	 */
	private static boolean isLeapYearFromInteger(int year) {
		boolean leapYear = false;
		try {
			if ((0 == year % 4) && (0 != year % 100) || (0 == year % 400)) {
				leapYear = true;
			}
		} catch (Exception e) {
			System.out.println("Unable to calculate leap year from integer due to mentioned exception :" + e);
		}
		return leapYear;
	}

	/**
	 * Name: isLeapYearFromString
	 * @author Swatee Shrivastava
	 * @creation date : 8-Oct-2020
	 * @version 1.0
	 * Description: This function is used to calculate leap year for String 
	 * @param yearStr - This parameter holds year value
	 * @return true/false
	 * Usage: boolean leapYear = isLeapYearFromString(yearStr);
	 */
	private static boolean isLeapYearFromString(String yearStr) {
		boolean leapYear = false;
		try {
			int year = Integer.parseInt(yearStr);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			leapYear = cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
		} catch (Exception e) {
			System.out.println("Unable to calculate leap year due to mentioned exception :" + e);
		}
		return leapYear;
	}

	/**
	 * Name: isLeapYearFromDate
	 * @author Swatee Shrivastava
	 * @creation date : 9-Oct-2020
	 * @version 1.0
	 * Description: This function is used to calculate leap year for date 
	 * @param dateStr - This parameter holds date 
	 * @return true/false
	 * Usage: boolean leapYear = isLeapYearFromDate(dateStr);
	 */
	private static boolean isLeapYearFromDate(Date dateStr, TimeZone timeZone) {
		boolean leapYear = false;
		try { 
			Calendar cal = Calendar.getInstance(timeZone);
			cal.setTime(dateStr);
			int year = cal.get(Calendar.YEAR);
			cal.set(Calendar.YEAR, year);
			leapYear = cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
		} catch (Exception e) {
			System.out.println("Unable to calculate leap year due to mentioned exception :" + e);
		} 
		return leapYear;
	}

	/**
	 * Name: getLastToken
	 * @author Swatee Shrivastava
	 * @creation date : 9-Oct-2020
	 * @version 1.0
	 * Description: This function is used to get last token from string
	 * @param strValue - This parameter holds string
	 * @param splitter - This parameter holds delimiter
	 * @return lastToken - Last token of the string
	 * Usage: String lastToken = getLastToken(strValue, splitter);
	 */
	private static String getLastToken(String strValue, String splitter )  {   
		String lastToken = null;
		try {
			if (strValue != null) {
				String[] strArray = strValue.split(splitter);  
				lastToken =  strArray[strArray.length -1];  
			}
		} catch (Exception e) {
			System.out.println("Unable to get last token of string due to mentioned exception :" + e);
		} 
		return lastToken;
	}  

	/**
	 * Name: calculateLeapYear
	 * @author Swatee Shrivastava
	 * @creation date : 12-Oct-2020
	 * @version 1.0
	 * Description: This function is used to calculate leap year for any object
	 * @param value - This parameter holds date value
	 * @param dateFormat - This parameter holds date format
	 * @param timeZone - This parameter holds date time zone area
	 * @return true/false 
	 * Usage: boolean leapYear = calculateLeapYear(value);
	 */
	public boolean calculateLeapYear(Object value, String dateFormat, TimeZone timeZone) {
		String yesrStr = null;
		boolean leapYear = false;
		try {
			if (value.getClass() == Integer.class) {
				leapYear = isLeapYearFromInteger((Integer) value);
			} else if (value.getClass() == String.class) {
				String dateStr = (String) value;
				if (dateStr.contains("/")) {
					SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);  
					Date date =  formatter.parse(dateStr);
					Calendar cal = Calendar.getInstance(timeZone);
					cal.setTime(date);
					int year = cal.get(Calendar.YEAR);
					leapYear = isLeapYearFromInteger(year);
				} else if (dateStr.contains("-")) {
					yesrStr = getLastToken(dateStr, "-");
					leapYear = isLeapYearFromString (yesrStr);
				} else {
					yesrStr = dateStr;
					leapYear = isLeapYearFromString (yesrStr);
				}		
			} else  {
				leapYear = isLeapYearFromDate((Date) value, timeZone);
			}
		} catch (Exception e) {
			System.out.println("Unable to calculate leap year due to mentioned exception :" + e);
		} 
		return leapYear;
	}

	/**
	 * Name: calculateNumOfLeapYearInRangeOfOneToYear
	 * @author Swatee Shrivastava
	 * @creation date : 12-Oct-2020
	 * @version 1.0
	 * Description: This function is used to calculate the number of leap years in range of (1, year) 
	 * @param year - This parameter holds year 
	 * @return numOfYears - Number of leap year in range
	 * Usage: int numOfYears = calculateNumOfLeapYearInRangeOfOneToYear(year);
	 */
	private static int calculateNumOfLeapYearInRangeOfOneToYear(int year) { 
		int numOfYears = 0;
		try {
			numOfYears =  (year / 4) - (year / 100) + (year / 400); 
		} catch (Exception e) {
			System.out.println("Unable to calculate leap year in range due to mentioned exception :" + e);
		}
		return numOfYears;
	} 

	/**
	 * Name: calculateNumOfLeapYearInRange
	 * @author Swatee Shrivastava
	 * @creation date : 12-Oct-2020
	 * @version 1.0
	 * Description: This function is used to calculate the number of leap years in given range  
	 * @param year - This parameter holds year 
	 * @return numOfYears - Number of leap year in range
	 * Usage: int numOfYears = calculateNumOfLeapYearInRange(year);
	 */
	public static int calculateNumOfLeapYearInRange(Object startYear, Object endYear) { 
		int numOfYears = 0;
		int startYearInt = covertObjectIntoInt(startYear);
		int endYearInt = covertObjectIntoInt(endYear);
		try {
			startYearInt--; 
			int numEndYear = calculateNumOfLeapYearInRangeOfOneToYear(endYearInt); 
			int numStartYear = calculateNumOfLeapYearInRangeOfOneToYear(startYearInt); 
			numOfYears = numEndYear - numStartYear;
		} catch (Exception e) {
			System.out.println("Unable to calculate leap year in range due to mentioned exception :" + e);
		}
		return numOfYears;
	} 
	/**
	 * Name: covertObjectIntoInt
	 * @author Swatee Shrivastava
	 * @creation date : 12-Oct-2020
	 * @version 1.0
	 * Description: This function is used to type case object into integer 
	 * @param value - This parameter holds object value
	 * @return intVale -Integer value of object
	 * Usage: int intVale = covertObjectIntoInt(value);
	 */
	private static int covertObjectIntoInt(Object value) {
		int intVale = 0;
		try {
			if (value.getClass() == String.class) {
				intVale = Integer.parseInt((String) value);
			} else if (value.getClass() == Integer.class) {
				intVale = (Integer) value;
			}	
		} catch (Exception e) {
			System.out.println("Unable to convert object into integer due to mentioned exception :" + e);
		}
		return intVale;
	}

	/**
	 * Name: listOfLeapYearInRange
	 * @author Swatee Shrivastava
	 * @creation date : 12-Oct-2020
	 * @version 1.0
	 * Description: This function is used to get list of leap years in range 
	 * @param startYear - This parameter holds starting year
	 * @param endYear - This parameter holds ending year
	 * @return leapYearList - List of leap years in range
	 * Usage: ArrayList<Integer> leapYearList = listOfLeapYearInRange(startYear, endYear);
	 */
	public static ArrayList<Integer> listOfLeapYearInRange(Object startYear, Object endYear) {
		int startYearInt = covertObjectIntoInt(startYear);
		int endYearInt = covertObjectIntoInt(endYear);
		ArrayList<Integer> leapYearList = new ArrayList<Integer>();
		try {
			for (int index = startYearInt; index <= endYearInt; index++) {
				if ((0 == index % 4) && (0 != index % 100) || (0 == index % 400)) {
					leapYearList.add(index);
				}
			}	
		} catch (Exception e) {
			System.out.println("Unable to get list of leap year in range due to mentioned exception :" + e); 
		}	
		return leapYearList; 
	}

	/**
	 * Name: getDateMonthYear
	 * @author Swatee Shrivastava
	 * @creation date : 02-Nov-2020
	 * @version 1.0
	 * Description: This function is used to get Date Month and year from string 
	 * @param value - This parameter holds date value
	 * @param dateFormat - This parameter holds date format
	 * @param timeZone - This parameter holds date time zone area
	 * Usage: String dateArray[] = getDateMonthYear(dateValue, dateFormat, timeZone);
	 */
	private static String[] getDateMonthYear(Object dateValue, 
			String dateFormat, TimeZone timeZone) throws ParseException {
		String [] dateArray = new String[3];
		Date date = null;
		if (dateValue.getClass() == String.class) {
			String dateStr = (String) dateValue;
			if (dateStr.contains("-")) {
				dateStr = dateStr.replaceAll("-", "/");
			}
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);  
			date =  formatter.parse(dateStr);
		} else {
			date = (Date) dateValue;
		}
		Calendar cal = Calendar.getInstance(timeZone);
		cal.setTime(date);
		dateArray[0] = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		dateArray[1] = Integer.toString(cal.get(Calendar.MONTH));
		dateArray[2] = Integer.toString(cal.get(Calendar.YEAR));
		return dateArray;
	}

	/**
	 * Name: selectDateFromDatePicker
	 * @author Swatee Shrivastava
	 * @creation date : 02-Nov-2020
	 * @version 1.0
	 * Description: This function is used to select date from date picker
	 * @param dateValue - This parameter holds date value
	 * @param dateFormat - This parameter holds date format
	 * @param timeZone - This parameter holds date time zone area
	 * @param dtpDatePickerElement : This parameter holds web element for date picker
	 * @param dtpMidLinkElement : This parameter holds web element for Mid link of date picker
	 * @param dtpNextLinkElement : This parameter holds web element for next navigation 
	 * @param dtpPreviousLinkElement : This parameter holds web element for  previous navigation 
	 * @param dtpAllMonthElement : This parameter holds list of web element for all months
	 * @param dtpAllDateElement : This parameter holds list of web element for all days
	 * Usage: selectDateFromDatePicker(dateValue, dateFormat, timeZone, 
	 * 					datePickerElement, midLinkElement, nextLinkElement, previousLinkElement
	 * 					listAllMonthElement, listAllDateElement);
	 */
	public static void selectDateFromDatePicker(Object dateValue, String dateFormat, TimeZone timeZone, 
			WebElement dtpDatePickerElement,WebElement dtpMidLinkElement,
			WebElement dtpNextLinkElement, WebElement dtpPreviousLinkElement,
			List<WebElement> dtpAllMonthElement, 
			List<WebElement> dtpAllDateElement) throws ParseException, InterruptedException {
		// DD/MM/YYYY format
		String dateArray[] = getDateMonthYear(dateValue, dateFormat, timeZone);
		//Click on button to open calendar
		dtpDatePickerElement.click();
		//Get the year difference between current year and year to set in calander
		int yearDiff = Integer.parseInt(dateArray[2])- Calendar.getInstance().get(Calendar.YEAR);
		dtpMidLinkElement.click();
		if (yearDiff > 0) {
			//Move to next year
			for (int index = 0; index < yearDiff; index++) {
				dtpNextLinkElement.click();
			}
		} else if (yearDiff < 0) {
			//Move to previous year
			for (int index = 0; index < (yearDiff * (-1)); index++) {
				dtpPreviousLinkElement.click();
			}
		}
		//Get all months from calendar to select correct one
		dtpAllMonthElement.get(Integer.parseInt(dateArray[1])).click();
		Thread.sleep(2000);
		//Get all dates from calendar to select correct one
		dtpAllDateElement.get(Integer.parseInt(dateArray[0]) - 1).click();
		Thread.sleep(2000);
	}

	/**
	 * Name: getCurrentDateTime
	 * @author Swatee Shrivastava
	 * @creation date : 17-Nov-2020
	 * @version 1.0
	 * Description: This function is used to get current date and time
	 * @param dateFormat - This parameter holds date format
	 * @param timeZone - This parameter holds date time zone area
	 * @Default : This function will return current time in milliseconds
	 * @return currDateTime : Current date and time in the given format 
	 * Usage: String currDateTime = getCurrentDateTime(dateFormat, timeZone)
	 */
	public String getCurrentDateTime(String dateFormat, TimeZone timeZone) {
		String currDateTime = null;
		try {
			if (dateFormat == null) {
				currDateTime = Long.toString(System.currentTimeMillis());
			} else {
				if (timeZone == null) {
					timeZone = TimeZone.getDefault();
				}

				DateFormat formatter = new SimpleDateFormat(dateFormat);
				Date date = new Date();
				formatter.setTimeZone(timeZone);
				currDateTime = formatter.format(date);
			}
		} catch (Exception exception){
			System.out.println("Unable to current date and time due to mentioned exception :" + exception);
		}
		return currDateTime;
	}

	/**
	 * Name: getNewDateFromGivenDate
	 * @author Swatee Shrivastava
	 * @creation date : 17-Nov-2020
	 * @version 1.0
	 * Description: This function is used to get New date from existing date
	 * @param dateStr - This parameter holds date from which new date need to calculate
	 * @param date - This parameter holds number of days difference from dateStr to calculate new date
	 * @param month - This parameter holds number of month difference from dateStr to calculate new date
	 * @param year - This parameter holds number of year difference from dateStr to calculate new date
	 * @return newDate
	 * Usage: getNewDateFromGivenDate(dateStr, date, month, year, dateFormat);
	 */
	public static String getNewDateFromGivenDate(Object dateValue, int date,
			int month, int year, String dateFormat) {
		String newDate = null;
		String dateStr = null;
		Date inputDate = null;
		try {
			DateFormat formatter = new SimpleDateFormat(dateFormat);
			if (dateValue.getClass() == String.class) {
				dateStr = (String) dateValue;
				if (dateStr.contains("-")) {
					dateStr = dateStr.replaceAll("-", "/");
				}
				inputDate = formatter.parse(dateStr);
			} else if (dateValue.getClass() == Date.class) {
				inputDate = (Date) dateValue;
			} else {
				System.out.println("Please enter date in valid format : Example String or Date");
				return newDate;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(inputDate);
			cal.add(Calendar.DATE, date);
			cal.add(Calendar.MONTH, month);
			cal.add(Calendar.YEAR, year);
			newDate = formatter.format(cal.getTime());
		} catch (Exception exception){
			System.out.println("Unable to get date due to mentioned exception :" + exception);
		}
		return newDate;
	}
}
