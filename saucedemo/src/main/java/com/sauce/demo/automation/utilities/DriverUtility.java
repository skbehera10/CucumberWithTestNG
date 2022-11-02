package com.sauce.demo.automation.utilities;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.sauce.demo.automation.base.BrowserFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DriverUtility extends BrowserFactory {

	private static Logger logger = Logger.getLogger(DriverUtility.class);

	public static void clickElementByText(By element, String text) {
		List<WebElement> list = driver.findElements(element);
		for (WebElement item : list) {
			if (item.isDisplayed() && (item.getText() == text)) {
				item.click();
				return;
			}
		}
	}

	public static void verifyElementText(WebElement element, String textToCompare, String stepName, String passStepStatus, String failStepStatus)
			throws Exception {
		waitForElementPresent(element);
		if (element.isDisplayed()) {
			logger.info("Element Text : " + element.getText());
			if (element.getText().trim().equals(textToCompare.trim())) {
				logger.info(element + "text displayed as expected");
			} else {
				Assert.fail(element + "displayed with invalid text");
			}
		} else {
			Assert.fail(element + "is not displayed on page");
		}
	}

	public static void verifyElementIsDisplayed(WebElement element) throws Exception {
		waitForElementPresent(element);
		if (element.isDisplayed()) {
			logger.info(element + "displayed");
		} else {
			Assert.fail(element + "not displayed");
		}
	}

	public static void clickElement(WebElement element, String elemName) throws Exception {
		waitForElementPresent(element);
		if (element.isDisplayed()) {
			element.click();
			logger.info("'" + elemName + "' clicked successfully");
		} else {
			Assert.fail("Not able to click '" + elemName + " '");
		}
	}

	public static void clickElement(WebElement element) throws Exception {
		waitForElementPresent(element);
		if (element.isDisplayed()) {
			element.click();
		} else {
			Assert.fail("Not able to click '" + element + " '");
		}
	}

	public static void JSClick(WebElement elementToClick) {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", elementToClick);
		js.executeScript("arguments[0].click();", elementToClick);
	}

	private static WebDriverWait webdriverWait() {
		return new WebDriverWait(driver, 90);
	}

	public static void waitForElementPresent(WebElement elementToWaitFor) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(elementToWaitFor));
		} catch (Exception ex) {
			ex.printStackTrace();
			assertThat(ex).withFailMessage("Element " + elementToWaitFor + " is not found").isInstanceOf(NoSuchElementException.class);
		}
	}

	public static void waitForElementInvisible(WebElement elementToWaitFor) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.invisibilityOf(elementToWaitFor));
		} catch (Exception ex) {
			ex.printStackTrace();
			assertThat(ex).withFailMessage("Element " + elementToWaitFor + " is still visible").isInstanceOf(NoSuchElementException.class);
		}
	}

	public static void waitForElementToBeClickable(WebElement elementToWaitFor) {
		try {
			webdriverWait().until(ExpectedConditions.elementToBeClickable(elementToWaitFor));
		} catch (Exception ex) {
			assertThat(ex).withFailMessage("Element " + elementToWaitFor + " is not clickable").isInstanceOf(ElementClickInterceptedException.class);
		}
	}

	public static String getSnapshotFolderPath() {
		String path = ConfigHelper.getInstance().getConfigProperty("ScreenShotPath");
		return path;
	}

	public static String takeScreenShot() throws IOException {
		logger.info("Taking snapshot");
		File srcFile = null;
		if (driver != null) {
			srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		String snapshotFileName = "screenshot" + dateFormat.format(cal.getTime()) + ".png";
		String pathToSnapshot = getSnapshotFolderPath() + "/" + snapshotFileName;
		FileUtils.copyFile(srcFile, new File(pathToSnapshot));
		return snapshotFileName;
	}

	public static void attachSnapshotToReport() {
		Path content = null;
		String snapshotFileName = null;
		try {
			snapshotFileName = takeScreenShot();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		content = Paths.get(getSnapshotFolderPath() + "/" + snapshotFileName);
		try (InputStream is = Files.newInputStream(content)) {
			// Allure.addAttachment(snapshotFileName, is);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean isElementDisplayed(WebElement element) {
		Boolean ele = element.isDisplayed();
		return ele;
	}

	public static boolean isElementPresent(By xpath) {
		try {
			driver.findElement(xpath);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void selectFromDropdownUsingVisibleText(WebElement elementOfDropdown, String visibleText) {
		Select select = new Select(elementOfDropdown);
		select.selectByVisibleText(visibleText);
	}

	public static void setValue(WebElement element, String value) throws InterruptedException {
		if (!value.trim().equalsIgnoreCase("<ignore>") && !value.trim().equals("")) {
			int lenght = element.getAttribute("value").length();
			if (lenght > 0) {
				element.sendKeys(Keys.END);
				for (int i = 1; i <= lenght; i++) {
					element.sendKeys(Keys.BACK_SPACE);
				}
			}
			element.sendKeys(value);
		}
	}

	public void rbSendKeys(Robot robot, String keys) {
		for (char c : keys.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (KeyEvent.CHAR_UNDEFINED == keyCode) {
				throw new RuntimeException("Key code not found for character '" + c + "'");
			}
			robot.keyPress(keyCode);
			robot.delay(100);
			robot.keyRelease(keyCode);
			robot.delay(100);
		}
	}

	public void selectElementUsingValue(WebElement selectElement, String value) {
		Select select = new Select(selectElement);
		select.deselectAll();
		select.selectByValue(value);
	}

}
