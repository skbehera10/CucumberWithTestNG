package com.sauce.demo.automation.locators;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.sauce.demo.automation.utilities.DriverUtility;
import com.sauce.demo.automation.utilities.WaitUtility;

public class HomePage {
	public static Logger log = Logger.getLogger(HomePage.class);

	@FindBy(xpath = "//*[@id=\"header_container\"]/div[2]/span")
	WebElement txt_Product;

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	
	public void ClickSignIn() throws Exception {
				
		DriverUtility.clickElement(txt_Product, "PRODUCTS");
		
		//WaitUtility.waitForSpecificTime(10);
		log.info("Sign IN Text is: " +txt_Product.getText());
		if (driver.getPageSource().contains("Sign in please")) {
			Assert.assertEquals("Login", driver.getTitle());
			log.info("Sign IN page displayed successfully");
		}
		else {
			log.info("Sign IN button not working");
			Assert.assertTrue(false);
			driver.close();
		}
	}
	
	public void HomePageVerification() throws Exception {

		if (txt_Product.getText() != "PRODUCTS") {
		//if (driver.getPageSource().contains("Sign in please")) {
			log.info("Sign In Page displayed");
			Assert.assertEquals("Swag Labs", driver.getTitle());
		}
		else {
			log.info("Login not working & Home Page not displayed");
			Assert.assertTrue(false);
			driver.close();
		}
	}

}