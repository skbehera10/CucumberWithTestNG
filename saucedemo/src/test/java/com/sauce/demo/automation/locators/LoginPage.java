package com.sauce.demo.automation.locators;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.sauce.demo.automation.utilities.DriverUtility;
import com.sauce.demo.automation.utilities.WaitUtility;

public class LoginPage {
	public static Logger log = Logger.getLogger(LoginPage.class);
	
	@FindBy(xpath = "//*[@id=\"user-name\"]")
	WebElement UserName;

	@FindBy(xpath = "//*[@id=\"password\"]")
	WebElement Password;

	@FindBy(xpath = "//*[@id=\"login-button\"]")
	WebElement btn_LogIn;
	
	@FindBy(xpath = "//*[@id=\"login_button_container\"]/div/form/div[3]/h3")
	WebElement txt_InvalidCredentials;


	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
	}


	public void EnterUserNameAndPassword(String usr, String pwd) throws Exception {
		UserName.sendKeys(usr);
		Password.sendKeys(pwd);
	}
	
	public void PINScreenVerification() throws Exception {

		if (driver.getPageSource().contains("Please enter sms code.")) {
			Assert.assertEquals("Login", driver.getTitle());
			log.info("PIN Page displayed");
		}
		else {
			log.info("Sign IN button not working & PIN screen not displayed");
			Assert.assertTrue(false);
			driver.close();
		}
	}
	
	public void login() throws Exception {
		DriverUtility.clickElement(btn_LogIn, "Log In");

	}
	
	public void UnsuccessfulLogin() {
		
		//if (driver.getPageSource().contains( "Invalid username or password.")) {
		if (txt_InvalidCredentials.getText().contains( "Username and password do not match any user in this service")) {
			log.info("Invalid username or password message has been entered");
			Assert.assertEquals("Swag Labs", driver.getTitle());
			driver.close();
		}
		else {
			log.info("Invalid username or password message not displayed");
			Assert.assertTrue(false);
			driver.close();
		}
	}
	

}
