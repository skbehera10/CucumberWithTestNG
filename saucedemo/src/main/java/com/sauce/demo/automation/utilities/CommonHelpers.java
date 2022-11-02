package com.sauce.demo.automation.utilities;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CommonHelpers {
	public static Logger log = Logger.getLogger(CommonHelpers.class);

	/**
	 * Function Name: stringCompare
	 * Description: This function is used to compare two string values
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 20/10/2020
	 * @param actVal - This string argument holds actual value (string 1)
	 * @param expVal - This string argument holds expected value (string 2) 
	 * @return boolen - This function will return 'true' if string are equal and 'false' if not equal
	 * Usage: boolean returnVal = stringCompare("string1", "string2");
	 */
	public static boolean stringCompare(String actVal, String expVal) {
		if (actVal.trim().toLowerCase().equals(expVal.trim().toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Function Name: valCompare
	 * Description: This function is used to compare any type of values
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 04/11/2020 
	 * @param expVal - This object argument holds expected value
	 * @param actVal - This object argument holds actual value 
	 * @return boolean - This function will return 'true' if values are equal and 'false' if not equal
	 * Usage: boolean returnVal = valCompare("obj1", "obj2");
	 */
	public static boolean valCompare(Object expVal, Object actVal) {
		if (actVal.getClass() == String.class) {
			String eVal = String.valueOf(expVal).trim();
			String aVal = String.valueOf(actVal).trim();
			if (eVal.toLowerCase().equals(aVal.toLowerCase())) {
				return true;
			} else {
				return false;
			}
		} else if (actVal.getClass() == Integer.class) {
			int eVal = (Integer) expVal;
			int aVal = (Integer) actVal;
			if (eVal == aVal) {
				return true;
			} else {
				return false;
			}
		} else if (actVal.getClass() == Double.class) {
			Double eVal = (Double) (expVal);
			Double aVal = (Double) actVal;
			if (eVal.equals(aVal)) {
				return true;
			} else {
				return false;
			}
		} else if (actVal.getClass() == Character.class) {
			String eVal = String.valueOf(expVal).trim();
			String aVal = String.valueOf(actVal).trim();
			if (eVal.toLowerCase().equals(aVal.toLowerCase())) {
				return true;
			} else {
				return false;
			}
		} else {
			Assert.fail("\nVerify the object value(s) type. Ex: Interger, String, etc..");
			return false;
		}
	}

	/**
	 * Function Name: scrollByPixel
	 * Description: This function is used to scroll page to specify x, y coordinates/ pixels
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 18/11/2020 
	 * @param driver - This is driver variable
	 * @param xPixels - This is X coordiane pixel value
	 * @param yPixels - This is Y coordiane pixel value
	 * Usage: scrollByPixel(driverObj, 200, 400);
	 */
	public static void scrollByPixel(WebDriver driver, int xPixels, int yPixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(xPixels, yPixels)");
	}

	/**
	 * Function Name: scrollByVisibleElement
	 * Description: This function is used to scroll down the webpage by the visibility of the element
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 18/11/2020 
	 * @param driver - This is driver variable
	 * @param element - This is webelement which need to be found by scrolling down
	 * Usage: scrollByVisibleElement(driverObj, element);
	 */
	public static void scrollByVisibleElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * Function Name: scrollPageEnd
	 * Description: This function is used to scroll down to the bottom of page
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 18/11/2020 
	 * @param driver - This is driver variable
	 * Usage: scrollPageEnd(driverObj);
	 */
	public static void scrollPageEnd(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scroll the web page till end
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * Function Name: scrollPageUp
	 * Description: This function is used to scroll up to the top of page
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 18/11/2020 
	 * @param driver - This is driver variable
	 * Usage: scrollPageUp(driverObj);
	 */
	public static void scrollPageUp(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scroll the web page till up
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	/**
	 * Function Name: scrollMostRight
	 * Description: This function is used to scroll horizontally towards most right
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 18/11/2020 
	 * @param driver - This is driver variable
	 * @param element - This is webelement to which the scroll right should happen
	 * Usage: scrollMostRight(driverObj, webelement);
	 */
	public static void scrollMostRight(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scroll to the most right
		js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth", element);
		;
	}

}
