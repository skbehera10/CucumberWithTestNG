package com.sauce.demo.automation.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sauce.demo.automation.base.BrowserFactory;

public class WaitUtility extends BrowserFactory {

	private static WebDriverWait webdriverWait() {
		return new WebDriverWait(driver, 60);
	}

	/**=========================================================================================
	 * Function Name: waitForElementPresent
	 * Description: This function is used to Wait till element is visible in the current page.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param elementToWaitFor - WebElement	 
	 * @return - 
	 * Usage: waitForElementPresent(btn_login);
	 *===========================================================================================*/
	public static void waitForElementPresent(WebElement elementToWaitFor) {
		try {
			webdriverWait().until(ExpectedConditions.visibilityOf(elementToWaitFor));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**=========================================================================================
	 * Function Name: waitForElementToBeClickable
	 * Description: This function is used to Wait till element is enable/clickable .
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param elementToWaitFor - WebElement	 
	 * @return - 
	 * Usage: waitForElementToBeClickable(btn_login);
	 *===========================================================================================*/
	public static void waitForElementToBeClickable(WebElement elementToWaitFor) {
		try {
			webdriverWait().until(ExpectedConditions.elementToBeClickable(elementToWaitFor));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**=========================================================================================
	 * Function Name: waitForElementToBeDisapear
	 * Description: This function is used to Wait till element is disappeared by iterating in a loop for 30 counts to check if object is visible, If visible wait for 2 sec else come out of loop.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param elementToWaitFor - WebElement	 
	 * @return - 
	 * Usage: waitForElementToBeDisappear(btn_login);
	 *===========================================================================================*/
	public static void waitForElementToBeDisappear(WebElement elementToWaitFor) throws Exception {
		boolean ObjVisible = true;

		for (int i = 1; i < 30; i++) {
			try {
				// Thread.sleep(2000);
				// webdriverWait().until(ExpectedConditions.invisibilityOf(elementToWaitFor));
				if (elementToWaitFor.isDisplayed()) {
					Thread.sleep(2000);
				} else {
					ObjVisible = false;
					break;
				}
			} catch (Exception ex) {
				/*assertThat(ex)
				.withFailMessage("Element " + elementToWaitFor + " is still visible")
				.isInstanceOf(ElementClickInterceptedException.class);*/
				ObjVisible = false;
				break;
			}
		}

		if (ObjVisible) {
			System.out.println(
					"The user is waiting till object : " + elementToWaitFor + " is disapeared,But after a 120 seconds the object is still visible .");
			// CustomReporter.flagResult("Fail", "The user is waiting till object : "+ elementToWaitFor +" is disapeared,But after a 120 seconds the
			// object is still visible .", "The wait time till invisibility of element not sucessfully", "yes");
		}
	}

	/**=========================================================================================
	 * Function Name: waitForPageToBeFullyLoaded
	 * Description: This function is used to Wait till page is fully loaded.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param - 	 
	 * @return - 
	 * Usage: waitForPageToBeFullyLoaded();
	 *===========================================================================================*/
	public static void waitForPageToBeFullyLoaded() {
		try {
			webdriverWait().until(WebDriver -> ((JavascriptExecutor) WebDriver).executeScript("return document.readyState").equals("complete"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**=========================================================================================
	 * Function Name: waitForSpecificTime
	 * Description: This function is used to Wait for user specified time interval.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param numOfSeconds - Seconds	 
	 * @return - 
	 * Usage: waitForSpecificTime(5000);
	 *===========================================================================================*/
	public static void waitForSpecificTime(long numOfSeconds) {
		try {
			Thread.sleep(numOfSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.println("Exception caught while waiting for " + numOfSeconds + "seconds");
		}
	}

	/**=========================================================================================
	 * Function Name: waitForTextWithItsHtmlTag
	 * Description: This function is used to Wait for element find by xpath (with user specified Html tag) having certain text.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param text - String Desired Text
	 * @param htmlTag - String HTML Tag Name	 
	 * @return - 
	 * Usage: waitForTextWithItsHtmlTag("Opportunity","h2");
	 *===========================================================================================*/
	public static void waitForTextWithItsHtmlTag(String text, String htmlTag) {
		try {
			webdriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//" + htmlTag + "[contains(text(),'" + text + "')])")));
		} catch (Exception ex) {

		}
	}

	/**=========================================================================================
	 * Function Name: waitForTextToBePresntInElement
	 * Description: This function is used to Wait for element having certain text.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param eleLocator - WebElement
	 * @param text - String TextToFound	 
	 * @return - 
	 * Usage: waitForTextToBePresntInElement(Label_OpportunityHeader","New Opportunity");
	 *===========================================================================================*/
	public static void waitForTextToBePresntInElement(WebElement eleLocator, String text) {
		try {
			webdriverWait().until(ExpectedConditions.textToBePresentInElement(eleLocator, text));
		} catch (Exception ex) {

		}
	}

	/**=========================================================================================
	 * Function Name: waitForAnAlert
	 * Description: This function is used to Wait for any alert box to appear.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param -  
	 * @return - 
	 * Usage: waitForAnAlert();
	 *===========================================================================================*/
	public static void waitForAnAlert() {
		try {
			webdriverWait().until(ExpectedConditions.alertIsPresent());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**=========================================================================================
	 * Function Name: waitForPageToBeLoadedWithSpecificTitle
	 * Description: This function is used to Wait for page to load with desired title.
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param pageTitle - String Page Title	 
	 * @return - 
	 * Usage: waitForPageToBeLoadedWithSpecificTitle("Home | New Opportunity");
	 *===========================================================================================*/
	public static void waitForPageToBeLoadedWithSpecificTitle(String pageTitle) {
		try {
			webdriverWait().until(ExpectedConditions.titleIs(pageTitle));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**=========================================================================================
	 * Function Name: waitForPageToBeLoadedWithSpecificPartialTitleText
	 * Description: This function is used to Wait for page to load with desired title as a partial text in the full title string
	 * @author : Santosh Behera
	 * @version 1.0
	 * @date 09/29/2020
	 * @param pageTitle - String Page Partial Title	Text
	 * @return - 
	 * Usage: waitForPageToBeLoadedWithSpecificPartialTitleText("New Opportunity");
	 *===========================================================================================*/
	public static void waitForPageToBeLoadedWithSpecificPartialTitleText(String pagePartialTitleText) {
		try {
			webdriverWait().until(ExpectedConditions.titleContains(pagePartialTitleText));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
